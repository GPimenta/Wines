//object Testing {
////  def main(args: Array[String]): Unit = {
////    print("Hi")
////  }
////@main def Hello(): Unit =
////  print("hello")
//}
@main def Hello(params: String*): Unit =
  print("hello: ")
  params.map(s => s.toUpperCase).foreach(s => printf("%s ", s))
  println(" ")
