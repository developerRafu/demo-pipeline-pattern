# Demo Pipeline Pattern

## What is this project?

This is a **demonstration of the Pipeline Pattern** - a powerful architectural design that processes data through a series of sequential steps. Think of it like an assembly line in a factory: each step does its job, passes the result to the next step, and the final product comes out at the end.

In this project, we're building a **Catalog Service** that fetches product information from multiple sources and combines them into a single response. Instead of making all requests at once and hoping they work, we use a pipeline to:

1. Load item details
2. Load inventory information
3. Load promotion data
4. Map everything together
5. Validate and enrich the response

### Key Features

- **Sequential Execution**: Steps run in a defined order (0 → 1 → 2 → 3 → 4 → 5)
- **Dependency Management**: Steps can depend on other steps and will wait for them to complete
- **Async & Sync Support**: Some steps run asynchronously (in parallel), others run synchronously (blocking)
- **Virtual Threads**: Uses Java 21+ virtual threads for lightweight concurrency
- **Error Handling**: Graceful error handling with detailed error messages
- **Validation Rules**: Post-processing rules validate and enrich the response

---

## How the Pipeline Engine Works

The `PipelineEngine` is the heart of this project. Here's how it works in simple terms:

### Step 1: Register All Steps
When the engine starts, it registers all steps in order (0 → 5). Each step gets a `CompletableFuture` that tracks when it completes.

```
PRE_LOAD(0) → LOAD_ITEM(1) → LOAD_INVENTORY(2) → LOAD_PROMOTION(3) → RESPONSE_MAPPING(4) → POST_MAPPING_RULES(5)
```

### Step 2: Schedule Steps in Order
The engine iterates through steps in order and schedules them:

- **Async Steps** (like LOAD_ITEM, LOAD_INVENTORY, LOAD_PROMOTION):
  - Run in a virtual thread (lightweight, non-blocking)
  - Wait for their dependencies to complete
  - Execute their logic
  - Mark themselves as done in the `Context`

- **Sync Steps** (like RESPONSE_MAPPING, POST_MAPPING_RULES):
  - Also run in a virtual thread (non-blocking)
  - Wait for their dependencies to complete
  - Execute their logic
  - Mark themselves as done

### Step 3: Wait for Completion
After all steps are scheduled, the engine waits for ALL of them to finish before returning.

```java
// Simplified flow:
for (Step step : steps) {
    if (step.shouldRun(context)) {
        scheduleStep(context, step);  // Register and start the step
    }
}
waitForCompletion(context);  // Wait for all to finish
```

### Dependency Resolution Example

Let's say `RESPONSE_MAPPING` depends on `LOAD_ITEM`, `LOAD_INVENTORY`, and `LOAD_PROMOTION`:

```java
@Override
public List<StepDefinition> dependsOn() {
    return List.of(
        StepDefinition.LOAD_ITEM,
        StepDefinition.LOAD_INVENTORY,
        StepDefinition.LOAD_PROMOTION
    );
}
```

When `RESPONSE_MAPPING` runs, it does this:

```java
// Get the futures of all dependencies
CompletableFuture<Void> dependencies = CompletableFuture.allOf(
    context.getFutureRequired(LOAD_ITEM),
    context.getFutureRequired(LOAD_INVENTORY),
    context.getFutureRequired(LOAD_PROMOTION)
);

// Wait for ALL of them to complete
dependencies.join();

// NOW execute this step
run(context, step);
```

This ensures that `RESPONSE_MAPPING` never runs until all three dependencies are done.

---

## The Steps Explained

### 1. **PRE_LOAD** (Order: 0)
Initialization step. Prepares the context before loading data.

### 2. **LOAD_ITEM** (Order: 1) - Async
Fetches product item details from a fake API client.
- Uses `ItemClient` (simulates API latency with random 100-600ms delay)
- Stores result in `context.item`

### 3. **LOAD_INVENTORY** (Order: 2) - Async
Fetches inventory/stock information from a fake API client.
- Uses `InventoryClient` (simulates API latency with random 100-600ms delay)
- Stores result in `context.inventory`

### 4. **LOAD_PROMOTION** (Order: 3) - Async
Fetches promotion/discount information from a fake API client.
- Uses `PromotionClient` (simulates API latency with random 100-600ms delay)
- Stores result in `context.promotion`

### 5. **RESPONSE_MAPPING** (Order: 4) - Sync
Combines all loaded data into a single response object.
- Depends on: LOAD_ITEM, LOAD_INVENTORY, LOAD_PROMOTION
- Uses `CatalogResponseMapper` to build the response
- Stores result in `context.catalogResponse`

### 6. **POST_MAPPING_RULES** (Order: 5) - Sync
Validates and enriches the response with calculated fields.
- Depends on: RESPONSE_MAPPING (implicitly, through order)
- Runs validation rules:
  - **PromotionRule**: Calculates promotional price and discount
  - **InventoryRule**: Determines if product is available

---

## Fake Clients (API Simulation)

This project uses **fake clients** to simulate real API calls with latency. This is great for testing because:

- ✅ No external dependencies
- ✅ Predictable behavior
- ✅ Can simulate delays (100-600ms)
- ✅ Can simulate different scenarios

### Example: ItemClient

```java
public ItemResponse getById(final String id) {
    SleepHelper.sleep("ItemClient");  // Simulate API latency
    return ItemResponse.builder()
        .id(id)
        .name("item name")
        .description("Some item description")
        // ... more fields
        .build();
}
```

The `SleepHelper` adds a random delay between 100-600 milliseconds to simulate real API response times.

---

## How to Run

### Prerequisites
- **Java 21+** (for virtual threads support)
- **Maven 3.8+**
- **Spring Boot 4.0.3+**

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Test the Pipeline

Make a GET request to fetch a catalog:

```bash
curl http://localhost:8080/api/catalog/{itemId}
```

Example:
```bash
curl http://localhost:8080/api/catalog/123
```

**Response:**
```json
{
  "item": {
    "id": "123",
    "name": "item name",
    "description": "Some item description",
    "categoryResponse": {
      "id": "categoryId01",
      "name": "category name",
      "description": "some category description"
    },
    "price": {
      "price": 100.00
    }
  },
  "promotion": {
    "id": "DEFAULT_PROMOTION_ID",
    "itemId": "123",
    "description": "DEFAULT_PROMOTION_DESCRIPTION",
    "percent": 5
  },
  "inventory": {
    "id": "inventoryId01",
    "itemId": "123",
    "quantity": 10,
    "available": true
  },
  "metadata": {
    "promotionalPrice": 5.00,
    "discountPrice": 95.00,
    "isAvailable": true
  }
}
```

---

## Project Structure

```
src/main/java/com/rafael/demopipelinepattern/
├── engine/
│   └── PipelineEngine.java          # The pipeline orchestrator
├── step/
│   ├── Step.java                    # Step interface
│   ├── AsyncStep.java               # Async step marker
│   ├── PreLoadStep.java
│   ├── LoadItemStep.java
│   ├── LoadInventoryStep.java
│   ├── LoadPromotionStep.java
│   ├── ResponseMappingStep.java
│   └── PostMappingStep.java
├── models/
│   ├── Context.java                 # Pipeline context (shared data)
│   ├── StepDefinition.java          # Step definitions with order
│   ├── Message.java                 # Error/warning messages
│   └── response/
│       ├── CatalogResponse.java
│       ├── ItemResponse.java
│       ├── InventoryResponse.java
│       ├── PromotionResponse.java
│       └── MetaDataResponse.java
├── client/
│   ├── ItemClient.java              # Fake item API
│   ├── InventoryClient.java         # Fake inventory API
│   └── PromotionClient.java         # Fake promotion API
├── mapper/
│   └── CatalogResponseMapper.java   # Maps context to response
├── validation/postload/
│   ├── ResponseRule.java            # Validation rule interface
│   ├── PromotionRule.java           # Calculates prices
│   ├── InventoryRule.java           # Checks availability
│   └── ItemValidation.java
├── exception/
│   ├── BadRequest.java              # Custom exception
│   └── GlobalExceptionHandler.java  # Exception handler
└── helper/
    └── SleepHelper.java             # Simulates API latency
```

---

## Key Concepts

### Context
A shared object that flows through the pipeline, carrying data from step to step.

```java
Context context = new Context(itemId);
// Step 1 populates context.item
// Step 2 populates context.inventory
// Step 3 populates context.promotion
// Step 4 combines them into context.catalogResponse
// Step 5 validates and enriches context.catalogResponse
```

### CompletableFuture
Used to track when each step completes. Allows other steps to wait for dependencies.

```java
// Step A registers its future
context.addFuture(LOAD_ITEM, futureA);

// Step B waits for Step A
CompletableFuture<Void> dependencies = context.getFutureRequired(LOAD_ITEM);
dependencies.join();  // Block until Step A is done
```

### Virtual Threads
Lightweight threads (Java 21+) that make concurrent code simpler and cheaper.

```java
// Instead of blocking a thread, virtual threads can be created cheaply
CompletableFuture.runAsync(() -> {
    // This runs in a virtual thread
    dependencies.join();
    run(context, step);
}, asyncExecutor);  // asyncExecutor uses virtual threads
```

---

## Error Handling

If a step fails:

- **Required steps**: Exception is thrown, pipeline stops
- **Optional steps**: Error is logged, error message is added to context, pipeline continues

```java
if (step.required()) {
    future.completeExceptionally(e);  // Stop the pipeline
} else {
    context.addError(Message.builder()
        .text("Error running step")
        .type(MessageType.WARNING)
        .module(MessageModule.ENGINE_MODULE)
        .details(List.of(e.getMessage()))
        .build());
    future.complete(null);  // Continue the pipeline
}
```

---

## Example Flow (Timeline)

```
Time 0ms:   Pipeline starts
Time 0ms:   PRE_LOAD scheduled
Time 0ms:   LOAD_ITEM scheduled (async)
Time 0ms:   LOAD_INVENTORY scheduled (async)
Time 0ms:   LOAD_PROMOTION scheduled (async)
Time 150ms: LOAD_ITEM completes (simulated delay)
Time 250ms: LOAD_INVENTORY completes (simulated delay)
Time 350ms: LOAD_PROMOTION completes (simulated delay)
Time 350ms: RESPONSE_MAPPING scheduled (waits for all 3 loads)
Time 350ms: RESPONSE_MAPPING executes (combines data)
Time 350ms: POST_MAPPING_RULES scheduled
Time 350ms: POST_MAPPING_RULES executes (validates)
Time 350ms: Pipeline completes
Total time: ~350ms (not 450ms, because async steps run in parallel!)
```

---

## Why This Pattern?

1. **Separation of Concerns**: Each step has one responsibility
2. **Reusability**: Steps can be reused in different pipelines
3. **Testability**: Each step can be tested independently
4. **Flexibility**: Easy to add, remove, or modify steps
5. **Performance**: Async steps run in parallel, reducing total time
6. **Maintainability**: Clear flow, easy to understand and debug

---

## Technologies Used

- **Java 21**: Virtual threads support
- **Spring Boot 4.0.3**: Dependency injection and web framework
- **Lombok**: Reduce boilerplate code
- **Maven**: Build and dependency management

---

## License

This is a demonstration project for educational purposes.
