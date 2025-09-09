package com.k1llm3sixy.deathpointer.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
    protected DeathScreenMixin(Text title) { super(title); }
    @Unique MinecraftClient mc = MinecraftClient.getInstance();
    @Unique ClientPlayerEntity player = mc.player;

    @Inject(method = "render", at = @At("HEAD"))
    private void addPosText(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int[] pos = getPlayerPos();
        String posText = String.format("§cX: §e%s §cY: §e%s §cZ: §e%s", pos[0], pos[1], pos[2]);
        context.drawCenteredTextWithShadow(this.textRenderer, posText, this.width / 2, 120, Colors.WHITE);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void addCopyBtn(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("position.copy"), (button) -> {
            int[] pos = getPlayerPos();
            mc.keyboard.setClipboard(pos[0] + " " + pos[1] + " " + pos[2]);
            button.active = false;
        }).dimensions(this.width / 2 + 100, this.height / 4 + 72, 80, 20).build());
    }

    @Unique
    private int[] getPlayerPos() {
        if (player == null) return new int[] {0, 0, 0};
        return new int[] {(int) player.getX(), (int) player.getY(), (int) player.getZ()};
    }
}