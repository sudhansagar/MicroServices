# Enterprise Java Design Patterns: Simple and Complex Examples

This guide is a quick, practical map from **simple** to **enterprise-scale** usage for common Java design patterns.
Each pattern has:
- **Simple example**: minimal usage to show intent.
- **Enterprise example**: realistic usage in layered apps, services, or distributed systems.

> Note: Examples are intentionally concise; they focus on shape and intent rather than full compilable projects.

---

## 1) Singleton
**Intent:** Ensure one instance; provide global access.

**Simple example**
```java
public final class AppConfig {
  private static final AppConfig INSTANCE = new AppConfig();
  private AppConfig() {}
  public static AppConfig getInstance() { return INSTANCE; }
}
```

**Enterprise example**
- Centralized feature flags loaded once at startup, cached, and updated via scheduled refresh.
```java
public final class FeatureFlags {
  private static final FeatureFlags INSTANCE = new FeatureFlags();
  private final Map<String, Boolean> flags = new ConcurrentHashMap<>();
  private FeatureFlags() { reload(); }
  public static FeatureFlags get() { return INSTANCE; }
  public boolean isOn(String key) { return flags.getOrDefault(key, false); }
  public synchronized void reload() { /* load from config service */ }
}
```

---

## 2) Factory Method
**Intent:** Let subclasses decide which class to instantiate.

**Simple example**
```java
interface Parser { void parse(String input); }
class JsonParser implements Parser { public void parse(String input) {} }
class XmlParser implements Parser { public void parse(String input) {} }

abstract class ParserFactory {
  abstract Parser create();
}
class JsonParserFactory extends ParserFactory {
  Parser create() { return new JsonParser(); }
}
```

**Enterprise example**
- Choose message parser by content type for a multi-tenant ingestion service.
```java
class ParserRegistry {
  private final Map<String, ParserFactory> factories;
  ParserRegistry(Map<String, ParserFactory> factories) { this.factories = factories; }
  Parser getParser(String contentType) { return factories.get(contentType).create(); }
}
```

---

## 3) Abstract Factory
**Intent:** Create families of related objects without specifying their classes.

**Simple example**
```java
interface Button { void render(); }
interface Dialog { void open(); }
interface UIThemeFactory {
  Button createButton();
  Dialog createDialog();
}
```

**Enterprise example**
- Produce cloud-vendor-specific clients (AWS/Azure/GCP) for storage, queue, and secrets.
```java
interface CloudFactory {
  StorageClient storage();
  QueueClient queue();
  SecretsClient secrets();
}
```

---

## 4) Builder
**Intent:** Construct complex objects step-by-step.

**Simple example**
```java
class User {
  private final String name;
  private final String email;
  private final boolean active;
  private User(Builder b){ this.name=b.name; this.email=b.email; this.active=b.active; }
  static class Builder {
    String name; String email; boolean active;
    Builder name(String v){ name=v; return this; }
    Builder email(String v){ email=v; return this; }
    Builder active(boolean v){ active=v; return this; }
    User build(){ return new User(this); }
  }
}
```

**Enterprise example**
- Build complex request objects (filters, pagination, sorting) for search APIs.
```java
SearchRequest req = new SearchRequest.Builder()
  .query("order").filter("status:PAID").page(3).size(50).sort("createdAt", DESC)
  .build();
```

---

## 5) Prototype
**Intent:** Clone existing objects instead of creating new ones.

**Simple example**
```java
class ReportTemplate implements Cloneable {
  String title; List<String> sections;
  public ReportTemplate clone() { /* shallow clone */ return (ReportTemplate) super.clone(); }
}
```

**Enterprise example**
- Clone base document templates for customer-specific reports.

---

## 6) Adapter
**Intent:** Convert interface of a class into another expected by clients.

**Simple example**
```java
class LegacyPaymentGateway { void payCents(int cents) {} }
interface PaymentGateway { void payMoney(BigDecimal amount); }
class PaymentAdapter implements PaymentGateway {
  private final LegacyPaymentGateway legacy;
  public void payMoney(BigDecimal amount) {
    legacy.payCents(amount.movePointRight(2).intValueExact());
  }
}
```

**Enterprise example**
- Wrap multiple vendor SDKs with a unified interface in a payments platform.

---

## 7) Facade
**Intent:** Provide a simplified interface to a complex subsystem.

**Simple example**
```java
class BillingFacade {
  void charge(Customer c, BigDecimal amount) {
    // calls tax, invoice, payment, email subsystems
  }
}
```

**Enterprise example**
- API gateway service exposes a single facade for order placement across inventory, pricing, and fulfillment.

---

## 8) Proxy
**Intent:** Provide a surrogate to control access to another object.

**Simple example**
```java
class ImageProxy implements Image {
  private RealImage image; private final String url;
  public void display(){ if(image==null) image = new RealImage(url); image.display(); }
}
```

**Enterprise example**
- Lazy-load remote configuration or add rate-limiting and metrics around a remote service client.

---

## 9) Decorator
**Intent:** Add responsibilities to objects dynamically.

**Simple example**
```java
interface Notifier { void send(String msg); }
class EmailNotifier implements Notifier { public void send(String msg){} }
class SlackDecorator implements Notifier {
  private final Notifier inner;
  public void send(String msg){ inner.send(msg); /* then Slack */ }
}
```

**Enterprise example**
- Add security, audit, and metrics to a service interface without modifying its implementation.

---

## 10) Composite
**Intent:** Compose objects into tree structures to represent part-whole hierarchies.

**Simple example**
```java
interface OrgUnit { int headCount(); }
class Team implements OrgUnit { public int headCount(){ return 5; } }
class Department implements OrgUnit {
  private final List<OrgUnit> children;
  public int headCount(){ return children.stream().mapToInt(OrgUnit::headCount).sum(); }
}
```

**Enterprise example**
- Model product bundles or organizational structures with consistent operations.

---

## 11) Flyweight
**Intent:** Share common object state to reduce memory usage.

**Simple example**
```java
class CountryCode {
  private final String code;
  private CountryCode(String code){ this.code = code; }
  private static final Map<String, CountryCode> CACHE = new HashMap<>();
  static CountryCode of(String code){ return CACHE.computeIfAbsent(code, CountryCode::new); }
}
```

**Enterprise example**
- Deduplicate frequently repeated reference data (currency, locale, status codes) in high-throughput services.

---

## 12) Strategy
**Intent:** Define a family of algorithms and make them interchangeable.

**Simple example**
```java
interface TaxStrategy { BigDecimal compute(BigDecimal amount); }
class IndiaTax implements TaxStrategy { public BigDecimal compute(BigDecimal amount){ return amount.multiply(new BigDecimal("0.18")); } }
```

**Enterprise example**
- Dynamic pricing strategies per region, tenant, or segment controlled by feature flags.

---

## 13) Template Method
**Intent:** Define skeleton of algorithm in base class; subclasses override steps.

**Simple example**
```java
abstract class FileImporter {
  public final void importFile(){ open(); parse(); save(); close(); }
  protected abstract void open();
  protected abstract void parse();
  protected abstract void save();
  protected void close() {}
}
```

**Enterprise example**
- Standard batch import pipeline with pluggable parsers, validations, and transformations.

---

## 14) Observer
**Intent:** Publish/subscribe for state changes.

**Simple example**
```java
interface Subscriber { void onEvent(String event); }
class EventBus {
  private final List<Subscriber> subs = new ArrayList<>();
  public void publish(String event){ subs.forEach(s -> s.onEvent(event)); }
}
```

**Enterprise example**
- Domain events emitted from microservices, consumed by analytics, notification, and audit services.

---

## 15) Command
**Intent:** Encapsulate a request as an object.

**Simple example**
```java
interface Command { void execute(); }
class ApproveOrderCommand implements Command { public void execute(){ /* approve */ } }
```

**Enterprise example**
- Workflow engines (e.g., order approval) where each step is a command with auditing and retry.

---

## 16) Chain of Responsibility
**Intent:** Pass a request along a chain of handlers.

**Simple example**
```java
abstract class Handler {
  protected Handler next;
  public Handler link(Handler next){ this.next = next; return next; }
  public void handle(Request r){ if(next != null) next.handle(r); }
}
```

**Enterprise example**
- API request pipeline: auth → validation → rate limit → business logic → logging.

---

## 17) State
**Intent:** Alter object behavior when its internal state changes.

**Simple example**
```java
interface OrderState { void next(Order order); }
class CreatedState implements OrderState { public void next(Order order){ order.setState(new PaidState()); } }
```

**Enterprise example**
- Order lifecycle management across distributed services (created, paid, shipped, delivered, canceled).

---

## 18) Mediator
**Intent:** Reduce coupling by centralizing communication.

**Simple example**
```java
interface ChatMediator { void send(String msg, User user); }
```

**Enterprise example**
- Service mesh or orchestration layer coordinating multiple services and sagas.

---

## 19) Iterator
**Intent:** Access elements of a collection without exposing internal representation.

**Simple example**
```java
Iterator<String> it = list.iterator();
while(it.hasNext()){ System.out.println(it.next()); }
```

**Enterprise example**
- Paging over large datasets via cursor-based iterators in data access layers.

---

## 20) Bridge
**Intent:** Separate abstraction from implementation so both can vary independently.

**Simple example**
```java
interface Renderer { void renderCircle(float radius); }
class Circle { private final Renderer renderer; public void draw(){ renderer.renderCircle(10); } }
```

**Enterprise example**
- Decouple domain services from transport (REST vs gRPC vs messaging) for different deployments.

---

## 21) Dependency Injection (DI)
**Intent:** Provide dependencies from outside rather than constructing inside.

**Simple example**
```java
class OrderService {
  private final PaymentGateway gateway;
  OrderService(PaymentGateway gateway){ this.gateway = gateway; }
}
```

**Enterprise example**
- Spring Boot application wiring repositories, services, and clients with profiles per environment.

---

## 22) Service Locator
**Intent:** Central registry for locating services.

**Simple example**
```java
class ServiceLocator {
  static <T> T get(Class<T> type){ /* lookup */ return null; }
}
```

**Enterprise example**
- Legacy systems retrieving services from JNDI or container-managed registries.

---

## 23) Repository
**Intent:** Encapsulate data access logic.

**Simple example**
```java
interface CustomerRepository { Customer findById(String id); }
```

**Enterprise example**
- Separate query/command repositories, with caching and read replicas.

---

## 24) Unit of Work
**Intent:** Track changes to objects and coordinate persistence.

**Simple example**
- A transaction boundary that batches inserts/updates before commit.

**Enterprise example**
- ORM session (e.g., Hibernate Session) managing dirty checking and flush semantics.

---

## 25) Data Transfer Object (DTO)
**Intent:** Simple objects for data transfer.

**Simple example**
```java
class OrderDTO { public String id; public String status; }
```

**Enterprise example**
- Separate internal domain models from external API contracts to avoid breaking changes.

---

## 26) DAO (Data Access Object)
**Intent:** Provide an abstract interface to the database.

**Simple example**
```java
class OrderDao { Order find(String id){ /* JDBC */ return null; } }
```

**Enterprise example**
- DAO layer with retry, metrics, query timeouts, and connection pooling.

---

## 27) MVC (Model-View-Controller)
**Intent:** Separate UI from business logic and data.

**Simple example**
- Spring MVC controller delegates to service and returns view.

**Enterprise example**
- Web and API tiers with controllers, DTOs, and services; client-side UI consumes API.

---

## 28) Front Controller
**Intent:** Single handler for all requests in a web app.

**Simple example**
- Servlet dispatcher routes to handlers.

**Enterprise example**
- API gateway or Spring DispatcherServlet routing, filters, and interceptors.

---

## 29) Interceptor / Filter
**Intent:** Pre/post-processing around a request.

**Simple example**
- Servlet filter logs request time.

**Enterprise example**
- Cross-cutting policies: auth, rate limiting, tracing, and metrics.

---

## 30) Circuit Breaker (Resilience Pattern)
**Intent:** Stop calling a failing service to prevent cascading failures.

**Simple example**
- Open circuit after N failures; return fallback.

**Enterprise example**
- Resilience4j circuit breaker with retries, timeouts, and bulkheads per downstream client.

---

## 31) Saga (Distributed Transaction Pattern)
**Intent:** Manage distributed transactions with compensating actions.

**Simple example**
- Sequence of local transactions with compensation on failure.

**Enterprise example**
- Orchestrated saga for order creation across payment, inventory, and shipping services.

---

## 32) CQRS (Command Query Responsibility Segregation)
**Intent:** Separate read/write models.

**Simple example**
- Commands update data; queries use read-optimized views.

**Enterprise example**
- Event-sourced write model with denormalized read projections.

---

## 33) Event Sourcing
**Intent:** Persist state changes as events.

**Simple example**
- Store append-only events and rebuild state on startup.

**Enterprise example**
- Kafka-backed event store with replay for audit and analytics.

---

## 34) API Gateway
**Intent:** Single entry point for clients.

**Simple example**
- Gateway routes to underlying services.

**Enterprise example**
- Gateway handles auth, rate limits, caching, and response aggregation.

---

## 35) Anti-Corruption Layer (ACL)
**Intent:** Translate between different domain models.

**Simple example**
- Adapter mapping legacy fields to new domain objects.

**Enterprise example**
- Separate integration module translating between ERP and new microservices.

---

## 36) Domain-Driven Design (DDD) Patterns
**Intent:** Model complex domains clearly.

**Simple example**
- Entities, value objects, aggregates.

**Enterprise example**
- Bounded contexts, context maps, and integration via domain events.

---

## How to Choose at Lead/Architect Level
- **Scope**: Use simple patterns inside services; enterprise patterns for cross-service workflows.
- **Cost**: Start with simplest pattern that solves the problem.
- **Consistency**: Apply the same patterns across teams to ease maintenance.
- **Observability**: Prefer patterns that make failure modes explicit (circuit breaker, saga, CQRS).

---

## Suggested Learning Path
1. GoF (Factory, Strategy, Observer, Adapter, Facade).
2. Structural & behavioral patterns (Decorator, Proxy, Template, Chain).
3. Enterprise patterns (Repository, Unit of Work, DTO, DI, MVC).
4. Distributed systems patterns (Saga, Circuit Breaker, CQRS, Event Sourcing).

If you want, I can provide a deeper, runnable project example for each pattern or focus on the patterns most used in your current stack.
