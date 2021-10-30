# Project One - Spring boot microservice

We do want to achieve following functionality with our microservice:

- RESTful API
    - GET `/api/v1/users`: retrieve a list of all users
    - POST `/api/v1/users`: create a new user with `firstName` and `lastName`
    - GET `/api/v1/users/same-name`: returns a list of `lastName` and `count(firstName)` (users with the same lastname)
    - DELETE `/api/v1/users/{userId}`: deletes a given user
- Database persistence
    - Create entities and repositories to fit the needs of the API

## Coding guide

<details>
<summary>Skip this section if you want to do it on your own</summary>

### Persistence

Let's start with the entity/repository layer. As the API requires us to deal with users, we create a User-Entity. In order to keep the
project tidied up, the entity should go in a separate package.

```kotlin
@Entity
class User(

  @Id
  var id: Int?,

  @Column
  var firstName: String,

  @Column
  var lastName: String
)
```

In order to perform queries on our entity a repository is required. Same here, you may move the repository to a separate package.

```kotlin
interface UserRepository : JpaRepository<User, Int>
```

### Service

Let's create a new service that interacts with the repository and prepares the data for the API.

```kotlin
interface UserService {
  fun getAllUsers(): List<User>
  fun createUser(firstName: String, lastName: String): User
  fun getUsersWithSameLastName(): List<SameUser>
  fun deleteUser(userId: Int)
}
```

```kotlin
data class SameUser(var lastName: String, var count: Int)
```

```kotlin
@Service
internal class UserServiceImpl(private val userRepository: UserRepository) : UserService {

  override fun getAllUsers(): List<User> {
    return userRepository.findAll()
  }

  override fun createUser(firstName: String, lastName: String): User {
    val user = User()
    user.firstName = firstName
    user.lastName = lastName
    return userRepository.safe(user)
  }

  override fun getUserWithSameLastName(): List<SameUser> {
    return userRepository.findAll()
      .groupBy { it.lastName }
      .map { SameUser(it.key, it.value.count()) }
  }

  override fun deleteUser(userId: Int) {
    userRepository.deleteById(userId)
  }
}
```

</details>