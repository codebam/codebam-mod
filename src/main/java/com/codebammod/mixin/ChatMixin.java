package com.codebammod.mixin;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {
	@Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = false)
	private void onChatMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
		ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler) (Object) this;
		ServerPlayerEntity player = handler.player;
		String message = packet.chatMessage();

		if (message.toLowerCase().contains("hello")) {
			player.sendMessage(Text.literal("Hi there, " + player.getName().getString() + "!"), false);
		}
	}
}
