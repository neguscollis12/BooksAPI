# Test Cases

## 1. Status: GET /status

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| ST-01 | P1 | GET /status | 200; body indicates the API is OK (e.g., "status": "OK") |
| ST-02 | P3 | Response time | Under 1-2 seconds |
| ST-03 | P3 | Response header | Content-Type: application/json |
| ST-04 | P3 | POST /status (unsupported method) | 404 or 405 |

## 2. List of Books: GET /books

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| BK-01 | P1 | GET /books with no parameters | 200; array of books, each with id, name, type, available |
| BK-02 | P2 | ?type=fiction | 200; every book has type = fiction |
| BK-03 | P2 | ?type=non-fiction | 200; every book has type = non-fiction |
| BK-04 | P2 | ?limit=5 | 200; at most 5 books |
| BK-05 | P2 | ?limit=1 (lower boundary) | 200; exactly 1 book |
| BK-06 | P2 | ?limit=20 (upper boundary) | 200; up to 20 books |
| BK-07 | P2 | ?type=fiction&limit=2 (combined) | 200; at most 2 books, all fiction |
| BK-08 | P3 | ?limit=0 (below boundary) | 400 with an error message |
| BK-09 | P3 | ?limit=21 (above boundary) | 400 with an error message |
| BK-10 | P3 | ?limit=-1 | 400 |
| BK-11 | P3 | ?limit=abc | 400 |
| BK-12 | P3 | ?type=comics (invalid value) | 400 |
| BK-13 | P3 | ?type=Fiction (case sensitivity) | Document actual behavior (400 or case-insensitive match) |
| BK-14 | P3 | Data types | id is an integer, name and type are strings, available is a boolean |
| BK-15 | P3 | Unknown query parameter (?foo=bar) | Ignored; 200 with the full list |

## 3. Get a Single Book: GET /books/:bookId

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| SB-01 | P1 | GET /books/1 | 200; detailed book info (id, name, author, isbn, type, price, current-stock, available) |
| SB-02 | P2 | Cross-check: ID and name match the entry returned by /books | Values are consistent |
| SB-03 | P3 | Non-existent ID (/books/9999) | 404 with an error message |
| SB-04 | P3 | /books/0 | 404 |
| SB-05 | P3 | /books/-1 | 404 |
| SB-06 | P3 | /books/abc | 404 or 400 |
| SB-07 | P3 | /books/1.5 | 404 or 400 |

## 4. API Authentication: POST /api-clients/

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| AU-01 | P1 | Register with a unique clientName and clientEmail | 201; body contains accessToken |
| AU-02 | P2 | Register the same name and email again | 409, "API client already registered." |
| AU-03 | P3 | Same email, different name | Document actual behavior (likely 409) |
| AU-04 | P3 | Missing clientName | 400 |
| AU-05 | P3 | Missing clientEmail | 400 |
| AU-06 | P3 | Empty body | 400 |
| AU-07 | P3 | Invalid email format ("notanemail") | 400 (or document if accepted) |
| AU-08 | P3 | Wrong data types (clientName: 123) | 400 |
| AU-09 | P3 | Malformed JSON | 400 |
| AU-10 | P3 | Very long clientName (e.g., 1000+ chars) | Handled gracefully; no 500 |
| AU-11 | P3 | Special characters/SQL/script strings in clientName | No 500; input is not executed |
| AU-12 | P2 | Token usability | The returned token works on a protected endpoint (for example GET /orders returns 200) |
| AU-13 | P3 | Token validity | Expires after 7 days (verify via documentation or a long-term check; not practical to test live) |

## 5. Submit an Order: POST /orders

Precondition: a valid token and a book ID with available: true.

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| OR-01 | P1 | Valid body (bookId: 1, customerName: "John") with a token | 201; body includes created: true and an orderId |
| OR-02 | P2 | Verify via GET /orders/{{orderId}} | Order data matches what was submitted |
| OR-03 | P3 | No Authorization header | 401 |
| OR-04 | P3 | Invalid token | 401 |
| OR-05 | P3 | Expired token (if one is available) | 401 |
| OR-06 | P3 | Malformed header (Bearer with no token, or wrong scheme) | 401 |
| OR-07 | P3 | Missing bookId | 400 |
| OR-08 | P3 | Missing customerName | 400 |
| OR-09 | P3 | Empty body | 400 |
| OR-10 | P3 | bookId as a string ("1") | 400 (or document if coerced) |
| OR-11 | P3 | bookId non-existent (9999) | 400 or 404 |
| OR-12 | P3 | bookId negative/zero | 400 |
| OR-13 | P3 | bookId of an unavailable book (available: false) | 404 or 400 with an explanatory message |
| OR-14 | P3 | customerName as a number or null | 400 |
| OR-15 | P3 | Empty customerName ("") | 400 (or document if accepted) |
| OR-16 | P3 | Extra, unexpected fields in the body | Ignored or rejected; no 500 |
| OR-17 | P3 | Wrong Content-Type (text/plain) | 400 or 415 |
| OR-18 | P3 | Special characters/unicode in customerName ("José O'Brien 🙂") | 201; stored and returned intact |
| OR-19 | P3 | Two identical orders in a row | Each gets a unique orderId |

## 6. Get All Orders: GET /orders

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| GA-01 | P1 | With a valid token | 200; array of orders |
| GA-02 | P2 | After creating an order | The new order appears in the list |
| GA-03 | P2 | New client with no orders | 200; empty array |
| GA-04 | P2 | Data isolation: Client A's token cannot see Client B's orders | Only the caller's orders are returned |
| GA-05 | P3 | No token | 401 |
| GA-06 | P3 | Invalid token | 401 |

## 7. Get an Order: GET /orders/:orderId

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| GO-01 | P1 | Valid orderId with token | 200; fields include id, bookId, customerName, createdBy, quantity, timestamp |
| GO-02 | P2 | Field values match the POST request | Consistent |
| GO-03 | P3 | Non-existent orderId | 404 |
| GO-04 | P3 | No token | 401 |
| GO-05 | P3 | Invalid token | 401 |
| GO-06 | P2 | Authorization: Client B requests Client A's orderId | 404 or 403; data is not exposed |
| GO-07 | P3 | Malformed orderId (special characters) | 404; no 500 |

## 8. Update an Order: PATCH /orders/:orderId

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| UP-01 | P1 | Valid customerName update | 204 (no content) |
| UP-02 | P1 | GET the order afterwards | customerName is updated; other fields (like bookId) are unchanged |
| UP-03 | P3 | No token | 401 |
| UP-04 | P3 | Invalid token | 401 |
| UP-05 | P3 | Non-existent orderId | 404 |
| UP-06 | P3 | Empty body | 400, or 204 with no change (document actual behavior) |
| UP-07 | P3 | customerName as a number or null | 400 |
| UP-08 | P3 | Attempt to update bookId (not allowed per docs) | Ignored or rejected; bookId remains unchanged |
| UP-09 | P3 | Malformed JSON | 400 |
| UP-10 | P2 | Authorization: Client B updates Client A's order | 404 or 403; order is unchanged |
| UP-11 | P3 | Update an already-deleted order | 404 |

## 9. Delete an Order: DELETE /orders/:orderId

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| DL-01 | P1 | Delete an existing order with a valid token | 204 |
| DL-02 | P1 | GET the deleted order | 404 |
| DL-03 | P2 | GET /orders after deletion | Order no longer in the list |
| DL-04 | P3 | Delete the same order twice | Second call returns 404 |
| DL-05 | P3 | No token | 401 |
| DL-06 | P3 | Invalid token | 401 |
| DL-07 | P3 | Non-existent orderId | 404 |
| DL-08 | P2 | Authorization: Client B deletes Client A's order | 404 or 403; order still exists |
| DL-09 | P3 | DELETE with a non-empty body | Document behavior (ignored or 400) |

## 10. End-to-End Flow

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| E2E-01 | P1 | Register client → GET /books → pick an available book → POST /orders → GET /orders/:id → PATCH name → GET to verify → DELETE → GET returns 404 | Every step returns the expected status and data |
| E2E-02 | P2 | Create 3 orders → GET /orders → delete one → GET /orders | List reflects 3, then 2 orders |
| E2E-03 | P3 | Create an order for a book, then check GET /books/:id | Document whether current-stock changes (the docs don't say) |

## 11. Cross-Cutting Checks (apply to all endpoints)

| ID | Pri | Scenario | Expected |
| --- | --- | --- | --- |
| X-01 | P2 | Content-Type header on all JSON responses | application/json |
| X-02 | P2 | Error responses have a consistent structure | Contain an error message field |
| X-03 | P3 | No 500 errors on any invalid input | Server errors are bugs |
| X-04 | P3 | Response time under normal load | Under about 2 seconds |
| X-05 | P3 | Token sent in a query string instead of a header | Rejected (401) |
| X-06 | P3 | HTTP (non-HTTPS) request | Redirected or refused |
| X-07 | P3 | Unsupported methods (PUT on /orders/:id, DELETE on /books) | 404 or 405 |
| X-08 | P3 | Light rate/load check (JMeter, small scale) | Stable responses; no unexpected errors |
