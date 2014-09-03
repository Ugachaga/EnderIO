package crazypants.enderio.power;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class PowerInterfaceRF implements IPowerInterface {
  private IEnergyHandler rfPower;

  public PowerInterfaceRF(IEnergyHandler powerReceptor) {
    rfPower = powerReceptor;
  }

  @Override
  public Object getDelegate() {
    return rfPower;
  }

  @Override
  public boolean canConduitConnect(ForgeDirection direction) {
    if(rfPower != null && direction != null) {
      return rfPower.canConnectEnergy(direction.getOpposite());
    }
    return false;
  }

  @Override
  public int getEnergyStored(ForgeDirection dir) {
    if(rfPower != null && dir != null) {
      return rfPower.getEnergyStored(dir);
    }
    return 0;
  }

  @Override
  public int getMaxEnergyStored(ForgeDirection dir) {
    if(rfPower != null && dir != null) {
      return rfPower.getMaxEnergyStored(dir);
    }
    return 0;
  }

  @Override
  public int getPowerRequest(ForgeDirection dir) {
    if(rfPower != null && dir != null && rfPower.canConnectEnergy(dir)) {
      return rfPower.receiveEnergy(dir, 99999999, true);
    }
    return 0;
  }


  public static int getPowerRequest(ForgeDirection dir, IEnergyHandler handler) {
    if(handler != null && dir != null && handler.canConnectEnergy(dir)) {
      return handler.receiveEnergy(dir, 99999999, true);
    }
    return 0;
  }

  @Override
  public int getMinEnergyReceived(ForgeDirection dir) {
    return 0;
  }

  @Override
  public int recieveEnergy(ForgeDirection opposite, float canOffer) {
    if(rfPower != null && opposite != null) {
      return rfPower.receiveEnergy(opposite, (int) (canOffer * 10), false);
    }
    return 0;
  }
}
