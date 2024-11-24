package com.codebammod

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

object Codebammod : ModInitializer {
  private val logger = LoggerFactory.getLogger("codebam-mod")

  override fun onInitialize() {
    CommandRegistrationCallback.EVENT.register { dispatcher, _, _ -> registerCommands(dispatcher) }
    logger.info("codebam-mod started!")
  }

  private fun registerCommands(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
            LiteralArgumentBuilder.literal<ServerCommandSource>("saysomething")
                    .then(
                            CommandManager.argument("message", StringArgumentType.string())
                                    .executes { context ->
                                      val message = StringArgumentType.getString(context, "message")
                                      val source = context.source
                                      source.server.playerManager.broadcast(
                                              Text.literal(message),
                                              false
                                      )
                                      Command.SINGLE_SUCCESS
                                    }
                    )
    )
  }
}
