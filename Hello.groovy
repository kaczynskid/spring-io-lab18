@RestController
class Greeter {

  @GetMapping("/greet")
  def greet() {
     return ["greeting": "Hello!"] 
  }
}
