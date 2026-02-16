# Repository Layer Tests

Currently, the OrderRepository does not contain any custom query methods.

These tests validate:

- Save operation
- Find by ID
- Find all
- Delete operation
- Correct JPA entity mapping
- Liquibase schema execution
- ID generation strategy

These are integration tests using @DataJpaTest.
