package net.omniblock.network.library.helpers.nbt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemNBTAPI {

    private static boolean compatible = true;
    private static boolean jsonCompatible = true;
    
    public static ItemNBTAPI instance;

    public static void initialize() {
    	new ItemNBTAPI().start();
    }
    
    public void start() {
    	
        instance = this;
        Logger.getAnonymousLogger().info("Running NBT reflection test...");
        
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setString(STRING_TEST_KEY, STRING_TEST_VALUE);
            nbtItem.setInteger(INT_TEST_KEY, INT_TEST_VALUE);
            nbtItem.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE);
            nbtItem.setBoolean(BOOLEAN_TEST_KEY, BOOLEAN_TEST_VALUE);
            nbtItem.setByte(BYTE_TEST_KEY, BYTE_TEST_VALUE);
            nbtItem.setShort(SHORT_TEST_KEY, SHORT_TEST_VALUE);
            nbtItem.setLong(LONG_TEST_KEY, LONG_TEST_VALUE);
            nbtItem.setFloat(FLOAT_TEST_KEY, FLOAT_TEST_VALUE);
            nbtItem.setIntArray(INTARRAY_TEST_KEY, INTARRAY_TEST_VALUE);
            nbtItem.setByteArray(BYTEARRAY_TEST_KEY, BYTEARRAY_TEST_VALUE);
            nbtItem.addCompound(COMP_TEST_KEY);
            NBTCompound comp = nbtItem.getCompound(COMP_TEST_KEY);
            comp.setString(STRING_TEST_KEY, STRING_TEST_VALUE+"2");
            comp.setInteger(INT_TEST_KEY, INT_TEST_VALUE*2);
            comp.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE*2);
             NBTList list = comp.getList("testlist", NBTType.NBTTagString);
             
            if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4){
            	
            	Logger.getAnonymousLogger().warning("Skipped the NBTList check, because 1.7 doesn't fully support it! The Item-NBT-API may not work!");
            	
            }else{
            	
                list.addString("test1");
                list.addString("test2");
                list.addString("test3");
                list.addString("test4");
                list.setString(2, "test42");
                list.remove(1);
                
            }
            
            NBTList taglist = comp.getList("complist", NBTType.NBTTagCompound);
            NBTListCompound lcomp = taglist.addCompound();
            lcomp.setDouble("double1", 0.3333);
            lcomp.setInteger("int1", 42);
            lcomp.setString("test1", "test1");
            lcomp.setString("test2", "test2");
            lcomp.remove("test1");

            item = nbtItem.getItem();
            nbtItem = null;
            comp = null;
            list = null;
            nbtItem = new NBTItem(item);


            if (!nbtItem.hasKey(STRING_TEST_KEY)) {
            	Logger.getAnonymousLogger().warning("Wasn't able to check a key! The Item-NBT-API may not work!");
                compatible = false;
            }
            if (!(STRING_TEST_VALUE).equals(nbtItem.getString(STRING_TEST_KEY))
                    || nbtItem.getInteger(INT_TEST_KEY) != INT_TEST_VALUE
                    || nbtItem.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE 
                    || nbtItem.getByte(BYTE_TEST_KEY) != BYTE_TEST_VALUE
                    || nbtItem.getShort(SHORT_TEST_KEY) != SHORT_TEST_VALUE 
                    || nbtItem.getFloat(FLOAT_TEST_KEY) != FLOAT_TEST_VALUE 
                    || nbtItem.getLong(LONG_TEST_KEY) != LONG_TEST_VALUE 
                    || nbtItem.getIntArray(INTARRAY_TEST_KEY).length != (INTARRAY_TEST_VALUE).length
                    || nbtItem.getByteArray(BYTEARRAY_TEST_KEY).length != (BYTEARRAY_TEST_VALUE).length
                    || !nbtItem.getBoolean(BOOLEAN_TEST_KEY).equals(BOOLEAN_TEST_VALUE)
                    ){
            	Logger.getAnonymousLogger().warning("One key does not equal the original value! The Item-NBT-API may not work!");
                compatible = false;
            }
            nbtItem.setString(STRING_TEST_KEY, null);
            if (nbtItem.getKeys().size() != 10) {
            	Logger.getAnonymousLogger().warning("Wasn't able to remove a key (Got " + nbtItem.getKeys().size()
                        + " when expecting 4)! The Item-NBT-API may not work!");
                compatible = false;
            }
            
            comp = nbtItem.getCompound(COMP_TEST_KEY);
            if (comp == null) {
            	Logger.getAnonymousLogger().warning("Wasn't able to get the NBTCompound! The Item-NBT-API may not work!");
                compatible = false;
            }
            if (!comp.hasKey(STRING_TEST_KEY)) {
            	Logger.getAnonymousLogger().warning("Wasn't able to check a compound key! The Item-NBT-API may not work!");
                compatible = false;
            }
            if (!(STRING_TEST_VALUE+"2").equals(comp.getString(STRING_TEST_KEY)) 
                    || comp.getInteger(INT_TEST_KEY) != INT_TEST_VALUE*2
                    || comp.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE *2
                    || comp.getBoolean(BOOLEAN_TEST_KEY) == BOOLEAN_TEST_VALUE) {
            	Logger.getAnonymousLogger().warning("One key does not equal the original compound value! The Item-NBT-API may not work!");
                compatible = false;
            }

            list = comp.getList("testlist", NBTType.NBTTagString);
            if(comp.getType("testlist") != NBTType.NBTTagList){
            	Logger.getAnonymousLogger().warning("Wasn't able to get the correct Tag type! The Item-NBT-API may not work!");
                compatible = false;
            }
            if(!list.getString(1).equals("test42") || list.size() != 3){
            	Logger.getAnonymousLogger().warning("The List support got an error, and may not work!");
            }
            taglist = comp.getList("complist", NBTType.NBTTagCompound);
            if(taglist.size() == 1){
                lcomp = taglist.getCompound(0);
                if(lcomp.getKeys().size() != 3){
                	Logger.getAnonymousLogger().warning("Wrong key amount in Taglist ("+ lcomp.getKeys().size() + ")! The Item-NBT-API may not work!");
                    compatible = false;
                }else{
                    if(lcomp.getDouble("double1") == 0.3333 && lcomp.getInteger("int1") == 42 && lcomp.getString("test2").equals("test2") && !lcomp.hasKey("test1")){
                        //ok
                    }else{
                    	Logger.getAnonymousLogger().warning("One key in the Taglist changed! The Item-NBT-API may not work!");
                        compatible = false;
                    }
                }
            }else{
                Logger.getAnonymousLogger().warning("Taglist is empty! The Item-NBT-API may not work!");
                compatible = false;
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            compatible = false;
        }

        testJson();

        String checkMessage = "Plugins that don't check properly, may throw Exeptions, crash or have unexpected behaviors!";
        if(compatible){
            if(jsonCompatible){
                Logger.getAnonymousLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
            }else{
                Logger.getAnonymousLogger().info("General Success! This version of Item-NBT-API is mostly compatible with your server. JSON serialization is not working properly. " + checkMessage);
            }
        }else{
            Logger.getAnonymousLogger().warning("WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! " + checkMessage);
        }

    }

    public void testJson(){
        if(!MinecraftVersion.hasGson()){
            Logger.getAnonymousLogger().warning("Wasn't able to find Gson! The Item-NBT-API may not work with Json serialization/deserialization!");
            return;
        }
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setObject(JSON_TEST_KEY, new SimpleJsonTestObject());

            item = nbtItem.getItem();

            if(!nbtItem.hasKey(JSON_TEST_KEY)){
                Logger.getAnonymousLogger().warning("Wasn't able to find JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
                jsonCompatible = false;
            }else{
                SimpleJsonTestObject simpleObject = nbtItem.getObject(JSON_TEST_KEY, SimpleJsonTestObject.class);
                if(simpleObject == null){
                    Logger.getAnonymousLogger().warning("Wasn't able to check JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
                    jsonCompatible = false;
                }else if (!(STRING_TEST_VALUE).equals(simpleObject.getTestString()) 
                        || simpleObject.getTestInteger() != INT_TEST_VALUE
                        || simpleObject.getTestDouble() != DOUBLE_TEST_VALUE 
                        || !simpleObject.isTestBoolean() == BOOLEAN_TEST_VALUE) {
                    Logger.getAnonymousLogger().warning("One key does not equal the original value in JSON! The Item-NBT-API may not work with Json serialization/deserialization!");
                    jsonCompatible = false;
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            Logger.getAnonymousLogger().warning(ex.getMessage());
            jsonCompatible = false;
        }
    }

    public boolean isCompatible() {
        return compatible;
    }

    public static NBTItem getNBTItem(ItemStack item){
        return new NBTItem(item);
    }

    //region STATIC FINAL VARIABLES
    private static final String STRING_TEST_KEY = "stringTest";
    private static final String INT_TEST_KEY = "intTest";
    private static final String DOUBLE_TEST_KEY = "doubleTest";
    private static final String BOOLEAN_TEST_KEY = "booleanTest";
    private static final String JSON_TEST_KEY = "jsonTest";
    private static final String COMP_TEST_KEY = "componentTest";
    private static final String SHORT_TEST_KEY = "shortTest";
    private static final String BYTE_TEST_KEY = "byteTest";
    private static final String FLOAT_TEST_KEY = "floatTest";
    private static final String LONG_TEST_KEY = "longTest";
    private static final String INTARRAY_TEST_KEY = "intarrayTest";
    private static final String BYTEARRAY_TEST_KEY = "bytearrayTest";

    private static final String STRING_TEST_VALUE = "TestString";
    private static final  int INT_TEST_VALUE = 42;
    private static final double DOUBLE_TEST_VALUE = 1.5d;
    private static final boolean BOOLEAN_TEST_VALUE = true;
    private static final short SHORT_TEST_VALUE = 64;
    private static final byte BYTE_TEST_VALUE = 7;
    private static final float FLOAT_TEST_VALUE = 13.37f;
    private static final long LONG_TEST_VALUE = (long)Integer.MAX_VALUE+42l;
    private static final int[] INTARRAY_TEST_VALUE = new int[]{1337, 42, 69};
    private static final byte[] BYTEARRAY_TEST_VALUE = new byte[]{8,7,3,2};
    //end region STATIC FINAL VARIABLES

    public static class SimpleJsonTestObject{
        private String testString = STRING_TEST_VALUE;
        private int testInteger = INT_TEST_VALUE;
        private double testDouble = DOUBLE_TEST_VALUE;
        private boolean testBoolean = BOOLEAN_TEST_VALUE;

        public SimpleJsonTestObject(){
        }

        public String getTestString() {
            return testString;
        }

        public void setTestString(String testString) {
            this.testString = testString;
        }

        public int getTestInteger() {
            return testInteger;
        }

        public void setTestInteger(int testInteger) {
            this.testInteger = testInteger;
        }

        public double getTestDouble() {
            return testDouble;
        }

        public void setTestDouble(double testDouble) {
            this.testDouble = testDouble;
        }

        public boolean isTestBoolean() {
            return testBoolean;
        }

        public void setTestBoolean(boolean testBoolean) {
            this.testBoolean = testBoolean;
        }
    }

}
