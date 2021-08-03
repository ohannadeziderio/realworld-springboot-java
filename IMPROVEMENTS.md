### Improvements

1. Refactor the architecture (suggestion)
    1. Clean Architecture (Dependency Rule)
        1. Entities
        2. Use Cases
        3. Adapters
        4. Frameworks
2. Break into microservices
3. Explain errors, warnings, etc. to the user
    1. Create more specific exceptions when needed
4. Instead of DTOs apply Converts to request template to respond
    1. To have more control over what's coming and going
5. Create BaseEntity with data common to all entities, such as:

    ```java
    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    ```