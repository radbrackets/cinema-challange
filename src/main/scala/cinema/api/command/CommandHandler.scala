package cinema.api.command

trait CommandHandler {
  type CommandType <: Command

  def handle(command: CommandType): Unit
}
