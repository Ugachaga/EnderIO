package crazypants.enderio.machines.machine.crafter;
/*package crazypants.enderio.base.machines.machine.crafter;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enderio.core.client.gui.GhostSlotHandler;
import com.enderio.core.client.gui.button.ToggleButton;
import com.enderio.core.client.gui.widget.GhostSlot;

import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.config.Config;
import crazypants.enderio.base.gui.IconEIO;
import crazypants.enderio.base.machines.machine.gui.GuiPoweredMachineBase;
import crazypants.enderio.base.network.GuiPacket;
import crazypants.enderio.base.power.PowerDisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GuiCrafter extends GuiPoweredMachineBase<TileCrafter> {

  private final ToggleButton bufferSizeB;

  public GuiCrafter(InventoryPlayer par1InventoryPlayer, TileCrafter te) {
    super(te, new ContainerCrafter(par1InventoryPlayer, te, null), "crafter");
    xSize = getXSize();

    int x = getXSize() - 5 - 16;
    int y = 43;
    bufferSizeB = new ToggleButton(this, 4327, x, y, IconEIO.ITEM_SINGLE, IconEIO.ITEM_STACK);
    bufferSizeB.setSelectedToolTip(EnderIO.lang.localize("gui.machine.bufferingstacks"));
    bufferSizeB.setUnselectedToolTip(EnderIO.lang.localize("gui.machine.bufferingsingle"));
    bufferSizeB.setSelected(te.isBufferStacks());
    recipeButton.setYOrigin(recipeButton.getBounds().y + 19);
  }

  @Override
  public void initGui() {
    super.initGui();
    bufferSizeB.onGuiInit();
    ((ContainerCrafter) inventorySlots).addCrafterSlots(getGhostSlotHandler());
  }

  @Override
  protected void mouseClickMove(int mouseX, int mouseY, int button, long par4) {
    GhostSlot slot = getGhostSlotHandler().getGhostSlotAt(this, mouseX, mouseY);
    if (slot != null) {
      ItemStack st = Minecraft.getMinecraft().player.inventory.getItemStack();
      // don't replace already set slots while dragging an item
      if (st.isEmpty() || slot.getStack().isEmpty()) {
        slot.putStack(st, st.getCount());
      }
    }
    super.mouseClickMove(mouseX, mouseY, button, par4);
  }

  @Override
  protected void actionPerformed(GuiButton b) throws IOException {
    super.actionPerformed(b);
    if (b == bufferSizeB) {
      getTileEntity().setBufferStacks(bufferSizeB.isSelected());
      GuiPacket.send(this, ContainerCrafter.EXEC_SET_BUFFER, bufferSizeB.isSelected());
    }
  }

  @Override
  public final int getXSize() {
    return 220;
  }

  @Override
  protected int getPowerU() {
    return 220;
  }

  @Override
  protected int getPowerX() {
    return 9;
  }

  @Override
  protected void updatePowerBarTooltip(List<String> text) {
    text.add(PowerDisplayUtil.formatPower(Config.crafterRfPerCraft) + " " + PowerDisplayUtil.abrevation() + " " + EnderIO.lang.localize("gui.machine.percraft"));
    super.updatePowerBarTooltip(text);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    bindGuiTexture();
    int sx = (width - xSize) / 2;
    int sy = (height - ySize) / 2;

    drawTexturedModalRect(sx, sy, 0, 0, xSize, ySize);

    super.drawGuiContainerBackgroundLayer(par1, par2, par3);
  }

}
*/