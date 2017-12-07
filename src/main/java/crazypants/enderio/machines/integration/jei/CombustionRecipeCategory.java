package crazypants.enderio.machines.integration.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.enderio.core.client.render.ColorUtil;

import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.Log;
import crazypants.enderio.base.fluid.IFluidCoolant;
import crazypants.enderio.base.fluid.IFluidFuel;
import crazypants.enderio.base.lang.LangFluid;
import crazypants.enderio.base.lang.LangPower;
import crazypants.enderio.machines.init.MachineObject;
import crazypants.enderio.machines.lang.Lang;
import crazypants.enderio.machines.machine.generator.combustion.CombustionMath;
import crazypants.enderio.machines.machine.generator.combustion.GuiCombustionGenerator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CombustionRecipeCategory extends BlankRecipeCategory<CombustionRecipeCategory.CombustionRecipeWrapper> {

  public static final @Nonnull String UID = "CombustionGenerator";

  // ------------ Recipes

  public static class CombustionRecipeWrapper extends BlankRecipeWrapper {

    private final FluidStack fluidCoolant, fluidFuel;
    private final CombustionMath math;

    private CombustionRecipeWrapper(FluidStack fluidCoolant, FluidStack fluidFuel, CombustionMath math) {
      this.fluidCoolant = fluidCoolant;
      this.fluidFuel = fluidFuel;
      this.math = math;
    }

    public void setInfoData(Map<Integer, ? extends IGuiIngredient<ItemStack>> ings) {
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
      ingredients.setInputs(FluidStack.class, Arrays.asList(fluidCoolant, fluidFuel));
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      FontRenderer fr = minecraft.fontRenderer;

      String txt = Lang.GUI_COMBGEN_OUTPUT.get(LangPower.RFt(math.getEnergyPerTick()));
      int sw = fr.getStringWidth(txt);
      fr.drawStringWithShadow(txt, 176 / 2 - sw / 2 - xOff, fr.FONT_HEIGHT / 2, ColorUtil.getRGB(Color.WHITE));

      int y = 21 - yOff - 2;
      int x = 114 - xOff;
      txt = LangFluid.tMB( math.getTicksPerCoolant() );
      sw = fr.getStringWidth(txt);
      fr.drawStringWithShadow(txt, x - sw / 2 + 7, y + fr.FONT_HEIGHT / 2 + 47, ColorUtil.getRGB(Color.WHITE));

      x = 48 - xOff;
      txt = LangFluid.tMB(math.getTicksPerFuel());
      sw = fr.getStringWidth(txt);
      fr.drawStringWithShadow(txt, x - sw / 2 + 7, y + fr.FONT_HEIGHT / 2 + 47, ColorUtil.getRGB(Color.WHITE));

      GlStateManager.color(1, 1, 1, 1);
    }

  } // -------------------------------------

  public static void register(IModRegistry registry, IGuiHelper guiHelper) {

    registry.addRecipeCategories(new CombustionRecipeCategory(guiHelper));
    registry.addRecipeCategoryCraftingItem(new ItemStack(MachineObject.block_combustion_generator.getBlockNN(), 1, 0), CombustionRecipeCategory.UID);
    registry.addRecipeClickArea(GuiCombustionGenerator.class, 155, 42, 16, 16, CombustionRecipeCategory.UID);

    long start = System.nanoTime();

    Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();

    List<CombustionRecipeWrapper> result = new ArrayList<CombustionRecipeWrapper>();

    for (Fluid fluid1 : fluids.values()) {
      IFluidCoolant coolant = CombustionMath.toCoolant(fluid1);
      if (coolant != null) {
        for (Fluid fluid2 : fluids.values()) {
          IFluidFuel fuel = CombustionMath.toFuel(fluid2);
          if (fuel != null) {
            CombustionMath math = new CombustionMath(coolant, fuel, 1f);
            result.add(new CombustionRecipeWrapper(new FluidStack(fluid1, 1000), new FluidStack(fluid2, 1000), math));
            // TODO: enhanced one
          }
        }
      }
    }

    long end = System.nanoTime();
    registry.addRecipes(result, UID);

    Log.info(String.format("TankRecipeCategory: Added %d combustion generator recipes to JEI in %.3f seconds.", result.size(), (end - start) / 1000000000d));
  }

  // ------------ Category

  // Offsets from full size gui, makes it much easier to get the location
  // correct
  static int xOff = 25;
  static int yOff = 7;
  static int xSize = 136;

  @Nonnull
  private final IDrawable background;

  public CombustionRecipeCategory(IGuiHelper guiHelper) {
    ResourceLocation backgroundLocation = EnderIO.proxy.getGuiTexture("combustion_gen");
    background = guiHelper.createDrawable(backgroundLocation, xOff, yOff, xSize, 70);
  }

  @Override
  public @Nonnull String getUid() {
    return UID;
  }

  @SuppressWarnings("null")
  @Override
  public @Nonnull String getTitle() {
    return MachineObject.block_combustion_generator.getBlock().getLocalizedName();
  }

  @Override
  public @Nonnull IDrawable getBackground() {
    return background;
  }

  @Override
  public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CombustionRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
    IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

    fluidStacks.init(0, true, 114 - xOff, 21 - yOff, 15, 47, 1000, false, null);
    fluidStacks.init(1, true, 48 - xOff, 21 - yOff, 15, 47, 1000, false, null);

    fluidStacks.set(ingredients);
  }

}
