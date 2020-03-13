CivModCore
===========

Versions:

 * 1.6.4 - Spigot 1.12.2

 * 1.5.10 - Spigot 1.11 or higher.

 * 1.5.9 - Spigot 1.10 / 1.10.x

No explicit backwards support is offered.

-------

Common Plugin Core derived from Humbug

To gain the most use from CivModCore, your plugin class should extend ACivMod:

    public class MyNewPlugin extends ACivMod {
    
    }
    
CivModCore implements onEnable/onLoad, and as such an extending plugin must Override and call super:

    @Override
    public void onEnable() {
        super.onEnable();
        // Do your stuff here that you need to do.
    }
    
    @Override
    public void onDisable() {
        // Do your stuff here that you need to do.
        super.onDisable();
    }
