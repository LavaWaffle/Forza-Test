# RomiBot
Repository for the Romi robot py Pololu for SPIKE 293

## How to use the program
1. Set up your ROMI robot following the directions in [WPILIB](https://docs.wpilib.org/en/stable/docs/romi-robot/index.html) 
> Make sure to complete steps ["Romi Hardware & Assembly"](https://docs.wpilib.org/en/stable/docs/romi-robot/hardware.html) and ["Imaging your Romi"](https://docs.wpilib.org/en/stable/docs/romi-robot/imaging-romi.html) 
2. Connect to the romi either through its [internet](https://docs.wpilib.org/en/stable/docs/romi-robot/imaging-romi.html#wireless-network-setup) or through a [bridge](https://docs.wpilib.org/en/stable/docs/romi-robot/web-ui.html#bridge-mode)
3. Go to the **build.gradle** file and change line 36 `wpi.sim.envVar("HALSIMWS_HOST", "10.0.0.124")` to `wpi.sim.envVar("HALSIMWS_HOST", "{localhost}")` where {localhost} is the IP address of your romi
> You can find the IP address of your romi through the romi web ui at [wpilibpi.local](https://wpilibpi.local/) in the **Network Settings** 
>> If your romi is connected through a [bridge](https://docs.wpilib.org/en/stable/docs/romi-robot/web-ui.html#bridge-mode) you can find the IP address of your romi by looking at the [localhost:10.0.0.1](localhost:10.0.0.1) (Your Routers Webpage)
4. Finally, press `CTRL + SHIFT + P` and choose the `WPILIB: Simulate Robot Code` option