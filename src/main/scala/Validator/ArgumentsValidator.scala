package Validator

object ArgumentsValidator {
  def validate(args: Seq[String]): Boolean = {
    args.length > 1
  }
}
