-- name: query-next-id
-- Get next ID from ids seq
select ids.nextval as id from dual

-- name: query-books
-- Get all them books
select _id, title, pages, read from books

-- name: query-book-by-id
-- Get book by ID
select _id, title, pages, read from books where _id = :id

-- name: query-book-langs
-- Get languages by book ID
select lang_id as lang
  from book_langs
  where book_id = :book_id

-- name: query-book-authors
select a.fname, a.lname
  from authors a
  inner join book_authors ba
    on a._id = ba.author_id
  where
    ba.book_id = :book_id
