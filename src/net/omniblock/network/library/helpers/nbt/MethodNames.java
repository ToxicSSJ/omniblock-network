package net.omniblock.network.library.helpers.nbt;

public class MethodNames {

    protected static String getTiledataMethodName(){
        MinecraftVersion v = MinecraftVersion.getVersion();
        if(v == MinecraftVersion.MC1_8_R3){
            return "b";
        }
        return "save";
    }

    protected static String getTypeMethodName(){
        MinecraftVersion v = MinecraftVersion.getVersion();
        if(v == MinecraftVersion.MC1_8_R3){
            return "b";
        }
        return "d";
    }

    @SuppressWarnings("unused")
	protected static String getEntitynbtgetterMethodName(){
        MinecraftVersion v = MinecraftVersion.getVersion();
        return "b";
    }

    @SuppressWarnings("unused")
	protected static String getEntitynbtsetterMethodName(){
        MinecraftVersion v = MinecraftVersion.getVersion();
        return "a";
    }
    
    protected static String getremoveMethodName(){
        MinecraftVersion v = MinecraftVersion.getVersion();
        if(v == MinecraftVersion.MC1_8_R3){
            return "a";
        }
        return "remove";
    }

}
