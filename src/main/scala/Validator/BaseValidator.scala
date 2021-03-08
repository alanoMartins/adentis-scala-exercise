package Validator

trait BaseValidator[T] {
  def validate(arg: T): Boolean
}
