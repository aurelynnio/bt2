# TÀI LIỆU API - ỨNG DỤNG QUẢN LÝ SÁCH

## Tổng quan
Ứng dụng REST API quản lý sách được xây dựng bằng Spring Boot, cung cấp các chức năng CRUD (Create, Read, Update, Delete) cho danh sách sách.

**Base URL:** `http://localhost:8080`

---

## Cấu trúc dự án

```
src/main/java/com/example/demo/
├── controller/
│   └── BookController.java     # Xử lý các HTTP requests
├── model/
│   └── Book.java               # Đối tượng Book với id, title, author
├── service/
│   └── BookService.java        # Logic nghiệp vụ quản lý sách
└── DemoApplication.java        # Class chạy ứng dụng
```

---

## Các API Endpoints

### 1. **GET /api/books** - Lấy danh sách tất cả sách

**Mô tả:** API này trả về danh sách tất cả các sách có trong hệ thống.

**Method:** `GET`

**URL:** `http://localhost:8080/api/books`

**Request:** Không cần body

**Response:** 
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "author": "John Doe"
  },
  {
    "id": 2,
    "title": "Spring Boot Guide",
    "author": "Jane Smith"
  },
  {
    "id": 3,
    "title": "REST API Design",
    "author": "Bob Johnson"
  }
]
```

**Status Code:** `200 OK`

**Giải thích:**
- Controller gọi method `getAllBooks()` của BookService
- BookService trả về danh sách tất cả các sách từ ArrayList
- Response trả về dạng JSON array

---

### 2. **GET /api/books/{id}** - Lấy thông tin sách theo ID

**Mô tả:** API này trả về thông tin chi tiết của một cuốn sách dựa trên ID.

**Method:** `GET`

**URL:** `http://localhost:8080/api/books/1`

**Path Variable:** 
- `id` (int): ID của sách cần lấy thông tin

**Request:** Không cần body

**Response:**
```json
{
  "id": 1,
  "title": "Java Programming",
  "author": "John Doe"
}
```

**Status Code:** `200 OK`

**Giải thích:**
- Controller nhận `id` từ URL path variable qua annotation `@PathVariable`
- BookService tìm kiếm sách theo ID trong ArrayList bằng stream filter
- Nếu tìm thấy: trả về object Book
- Nếu không tìm thấy: trả về null

---

### 3. **POST /api/books** - Thêm sách mới

**Mô tả:** API này thêm một cuốn sách mới vào hệ thống.

**Method:** `POST`

**URL:** `http://localhost:8080/api/books`

**Request Body:**
```json
{
  "id": 4,
  "title": "New Book",
  "author": "Author Name"
}
```

**Response:**
```
Book added successfully!
```

**Status Code:** `200 OK`

**Giải thích:**
- Controller nhận dữ liệu JSON từ request body qua annotation `@RequestBody`
- Spring tự động convert JSON thành object Book
- BookService thêm sách mới vào ArrayList bằng method `add()`
- Response trả về message xác nhận thêm thành công

**Lưu ý:** 
- Content-Type header phải là `application/json`
- Dữ liệu gửi lên phải đúng format JSON

---

### 4. **PUT /api/books/{id}** - Cập nhật thông tin sách

**Mô tả:** API này cập nhật thông tin (title và author) của một cuốn sách đã tồn tại.

**Method:** `PUT`

**URL:** `http://localhost:8080/api/books/1`

**Path Variable:** 
- `id` (int): ID của sách cần cập nhật

**Request Body:**
```json
{
  "title": "Updated Title",
  "author": "Updated Author"
}
```

**Response:**
```
Book updated successfully!
```

**Status Code:** `200 OK`

**Giải thích:**
- Controller nhận `id` từ path variable và dữ liệu cập nhật từ request body
- BookService tìm sách theo ID trong ArrayList
- Nếu tìm thấy: cập nhật `title` và `author` bằng setter methods
- Response trả về message xác nhận cập nhật thành công

**Lưu ý:**
- Chỉ cập nhật title và author, không cập nhật ID
- Nếu ID không tồn tại, không có thông báo lỗi (có thể cải thiện)

---

### 5. **DELETE /api/books/{id}** - Xóa sách theo ID

**Mô tả:** API này xóa một cuốn sách khỏi hệ thống dựa trên ID.

**Method:** `DELETE`

**URL:** `http://localhost:8080/api/books/1`

**Path Variable:** 
- `id` (int): ID của sách cần xóa

**Request:** Không cần body

**Response:**
```
Book deleted successfully!
```

**Status Code:** `200 OK`

**Giải thích:**
- Controller nhận `id` từ path variable
- BookService sử dụng `removeIf()` để xóa sách khỏi ArrayList
- `removeIf()` lọc và xóa các phần tử thỏa mãn điều kiện (book.getId() == id)
- Response trả về message xác nhận xóa thành công

---

## Chi tiết các thành phần

### 1. Model - Book.java

**Chức năng:** Định nghĩa cấu trúc dữ liệu của một cuốn sách

**Các thuộc tính:**
- `id` (int): Mã định danh duy nhất của sách
- `title` (String): Tên sách
- `author` (String): Tác giả

**Annotations Lombok:**
- `@Getter`: Tự động tạo getter methods cho tất cả các fields
- `@Setter`: Tự động tạo setter methods cho tất cả các fields
- `@NoArgsConstructor`: Tạo constructor không tham số
- `@AllArgsConstructor`: Tạo constructor với tất cả tham số

**Tại sao dùng Lombok?**
- Giảm boilerplate code (không cần viết getter/setter thủ công)
- Code ngắn gọn, dễ đọc, dễ bảo trì
- Tự động sinh code tại compile time

---

### 2. Service - BookService.java

**Chức năng:** Chứa logic nghiệp vụ xử lý dữ liệu sách

**Annotation:** `@Service` - Đánh dấu đây là một Spring Service component

**Thuộc tính:**
- `books` (List<Book>): Danh sách lưu trữ các sách (sử dụng ArrayList)

**Constructor:**
- Khởi tạo danh sách với 3 cuốn sách mẫu ban đầu
- Giúp có dữ liệu để test ngay khi khởi động ứng dụng

**Các methods:**

1. **getAllBooks()**: Trả về toàn bộ danh sách sách
   - Return type: `List<Book>`
   - Đơn giản chỉ return biến `books`

2. **getBookById(int id)**: Tìm và trả về sách theo ID
   - Sử dụng Java Stream API
   - `filter()`: lọc sách có ID khớp
   - `findFirst()`: lấy kết quả đầu tiên
   - `orElse(null)`: trả về null nếu không tìm thấy

3. **addBook(Book book)**: Thêm sách mới vào danh sách
   - Sử dụng method `add()` của ArrayList

4. **updateBook(int id, Book updatedBook)**: Cập nhật thông tin sách
   - Tìm sách theo ID bằng stream filter
   - Nếu tìm thấy: sử dụng `ifPresent()` để cập nhật title và author

5. **deleteBook(int id)**: Xóa sách khỏi danh sách
   - Sử dụng `removeIf()` với lambda expression
   - Xóa tất cả sách có ID khớp

---

### 3. Controller - BookController.java

**Chức năng:** Nhận HTTP requests và trả về responses

**Annotations:**
- `@RestController`: Kết hợp @Controller + @ResponseBody
  - Tự động convert return value thành JSON
  - Không cần thêm @ResponseBody cho mỗi method

- `@RequestMapping("/api/books")`: Định nghĩa base URL cho tất cả endpoints trong controller

- `@Autowired`: Inject BookService vào controller (Dependency Injection)
  - Spring tự động tạo và quản lý instance của BookService

**Các methods mapping:**

1. **getAllBooks()** - `@GetMapping`
   - Không có path thêm → URL: `/api/books`
   - Gọi service method và return kết quả

2. **getBookById()** - `@GetMapping("/{id}")`
   - `@PathVariable int id`: Lấy ID từ URL path
   - URL: `/api/books/1` → id = 1

3. **addBook()** - `@PostMapping`
   - `@RequestBody Book book`: Nhận JSON từ request body
   - Spring tự động deserialize JSON → Book object

4. **updateBook()** - `@PutMapping("/{id}")`
   - Nhận cả path variable (id) và request body (updatedBook)

5. **deleteBook()** - `@DeleteMapping("/{id}")`
   - Chỉ cần path variable để xác định sách cần xóa

---

## Luồng xử lý request

### Ví dụ: GET /api/books

1. **Client gửi request:** 
   ```
   GET http://localhost:8080/api/books
   ```

2. **DispatcherServlet (Spring MVC):**
   - Nhận request
   - Tìm Controller và method phù hợp (BookController.getAllBooks())

3. **BookController:**
   - Method `getAllBooks()` được gọi
   - Gọi `bookService.getAllBooks()`

4. **BookService:**
   - Method `getAllBooks()` trả về List<Book>

5. **Spring MVC:**
   - Tự động convert List<Book> → JSON
   - Thêm HTTP headers (Content-Type: application/json)

6. **Response trả về client:**
   ```json
   [
     {"id": 1, "title": "Java Programming", "author": "John Doe"},
     ...
   ]
   ```

---

## Cách test API với Postman

### 1. GET - Lấy tất cả sách
- Method: GET
- URL: `http://localhost:8080/api/books`
- Click "Send"

### 2. GET - Lấy sách theo ID
- Method: GET
- URL: `http://localhost:8080/api/books/1`
- Click "Send"

### 3. POST - Thêm sách mới
- Method: POST
- URL: `http://localhost:8080/api/books`
- Tab "Body" → chọn "raw" → chọn "JSON"
- Nhập:
  ```json
  {
    "id": 4,
    "title": "New Book",
    "author": "Author Name"
  }
  ```
- Click "Send"

### 4. PUT - Cập nhật sách
- Method: PUT
- URL: `http://localhost:8080/api/books/1`
- Tab "Body" → chọn "raw" → chọn "JSON"
- Nhập:
  ```json
  {
    "title": "Updated Title",
    "author": "Updated Author"
  }
  ```
- Click "Send"

### 5. DELETE - Xóa sách
- Method: DELETE
- URL: `http://localhost:8080/api/books/1`
- Click "Send"

---

## Kiến trúc ứng dụng

```
┌─────────────┐
│   Client    │ (Browser, Postman, Mobile App)
└──────┬──────┘
       │ HTTP Request
       ↓
┌─────────────────────┐
│  BookController     │ @RestController
│  - getAllBooks()    │ @GetMapping, @PostMapping, etc.
│  - getBookById()    │ @PathVariable, @RequestBody
│  - addBook()        │
│  - updateBook()     │
│  - deleteBook()     │
└──────┬──────────────┘
       │ calls
       ↓
┌─────────────────────┐
│  BookService        │ @Service
│  - List<Book>       │ Business Logic
│  - getAllBooks()    │
│  - getBookById()    │
│  - addBook()        │
│  - updateBook()     │
│  - deleteBook()     │
└──────┬──────────────┘
       │ manages
       ↓
┌─────────────────────┐
│  Book Model         │ @Getter @Setter
│  - id: int          │ @NoArgsConstructor
│  - title: String    │ @AllArgsConstructor
│  - author: String   │
└─────────────────────┘
```

---

## Các concepts quan trọng

### 1. REST API
- **RE**presentational **S**tate **T**ransfer
- Sử dụng HTTP methods: GET, POST, PUT, DELETE
- Stateless: mỗi request độc lập
- Resource-based: URL đại diện cho tài nguyên (/api/books)

### 2. Dependency Injection (DI)
- BookController không tự tạo BookService
- Spring tự động inject (cung cấp) BookService qua @Autowired
- Loose coupling, dễ test, dễ maintain

### 3. Annotation-driven Programming
- Sử dụng annotations thay vì XML configuration
- @RestController, @Service, @GetMapping, etc.
- Code ngắn gọn, dễ hiểu

### 4. MVC Pattern
- **Model**: Book.java (dữ liệu)
- **View**: JSON response (hiển thị)
- **Controller**: BookController.java (xử lý request)

---

## Cải tiến có thể thực hiện

### 1. Exception Handling
- Thêm @ControllerAdvice để xử lý lỗi toàn cục
- Trả về HTTP status code phù hợp (404 Not Found, 400 Bad Request)

### 2. Validation
- Thêm @Valid và validation annotations (@NotNull, @NotEmpty)
- Validate dữ liệu input trước khi xử lý

### 3. Database Integration
- Thay ArrayList bằng JPA/Hibernate
- Lưu trữ dữ liệu persistent trong database

### 4. Response DTO
- Tạo separate Response objects
- Tách biệt Model và Response structure

### 5. Logging
- Thêm logging để track requests và debug
- Sử dụng SLF4J + Logback

---

## Tổng kết

Ứng dụng này minh họa cách xây dựng REST API cơ bản với Spring Boot:
- ✅ Cấu trúc rõ ràng, phân tách concerns
- ✅ Sử dụng Spring annotations
- ✅ Đầy đủ CRUD operations
- ✅ RESTful design principles
- ✅ JSON request/response
- ✅ Dependency Injection pattern

**Ứng dụng đang chạy tại:** `http://localhost:8080`

**Thời gian phát triển:** Dự án demo phù hợp cho học tập và prototype
