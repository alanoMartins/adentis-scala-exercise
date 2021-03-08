package Validator

object ArgumentsValidator extends BaseValidator[Seq[String]] {
  def validate(args: Seq[String]): Boolean = {
    args.length > 1
  }
}
