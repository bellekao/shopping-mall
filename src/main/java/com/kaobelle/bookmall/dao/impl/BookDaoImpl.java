package com.kaobelle.bookmall.dao.impl;

import com.kaobelle.bookmall.dao.BookDao;
import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.rowmapper.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Book> getBooks(BookQueryParams bookQueryParams) {
        String sql = "SELECT book_id, title, author, category, image_url, price, stock, sale FROM `book`" +
                " WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();
        // Filtering
        if (bookQueryParams.getCategory() != null) {
            sql = sql + " AND category = :category";
            map.put("category", bookQueryParams.getCategory().name());
        }
        if (bookQueryParams.getSearch() != null) {
            sql = sql + " AND title LIKE :search";
            map.put("search", "%" + bookQueryParams.getSearch() + "%");
        }
        // Sorting
        sql = sql + " ORDER BY " + bookQueryParams.getOrderBy() + " " + bookQueryParams.getSort();


        List<Book> bookList = namedParameterJdbcTemplate.query(sql, map, new BookRowMapper());

        return bookList;
    }

    @Override
    public Book getBookById(Integer bookId) {
        String sql = "SELECT book_id, title, author, category, image_url, price, stock, sale FROM `book` WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        List<Book> bookList = namedParameterJdbcTemplate.query(sql, map, new BookRowMapper());

        if (bookList.size() > 0) {
            return  bookList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        String sql = "INSERT INTO `book`(title, author, category, image_url, price, stock, sale)" +
                " VALUES(:title, :author, :category, :imageUrl, :price, :stock, :sale)";

        Map<String, Object> map = new HashMap<>();
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("category", bookRequest.getCategory().toString());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("stock", bookRequest.getStock());
        map.put("sale", 0);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer bookId = keyHolder.getKey().intValue();

        return bookId;
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        String sql = "UPDATE `book` SET title = :title, author = :author, category = :category," +
                " image_url = :imageUrl, price = :price, stock = :stock" +
                " WHERE book_id = :bookId;";
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("category", bookRequest.getCategory().toString());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("stock", bookRequest.getStock());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void deleteBookById(Integer bookId) {
        String sql = "DELETE FROM `book` WHERE book_id = :bookId;";
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStock(Integer bookId, Integer stock) {
        String sql = "UPDATE `book` SET stock = :stock WHERE book_id = :bookId;";
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);
        map.put("stock", stock);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }
}
