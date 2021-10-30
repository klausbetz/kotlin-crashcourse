package at.project.one

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProjectOneApplication

fun main(args: Array<String>) {
  runApplication<ProjectOneApplication>(*args)
}
