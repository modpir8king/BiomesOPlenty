package biomesoplenty.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.Blocks;
import biomesoplenty.blocks.renderers.FoliageRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOPFlower extends BlockFlower
{
    private static final String[] plants = new String[] {"clover", "swampflower", "deadbloom", "glowflower", "hydrangea", "daisy", "tulip", "wildflower", "violet", "anemone", "toadstool", "cactus", "aloe", "portobello", "bluemilk", "dandelion"};
    private Icon[] textures;
    
    protected BlockBOPFlower(int blockID, Material material)
    {
        super(blockID, material);
        this.setTickRandomly(true);
        float var4 = 0.2F;
        this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public BlockBOPFlower(int blockID)
    {
        this(blockID, Material.plants);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        textures = new Icon[plants.length];
        
        for (int i = 0; i < plants.length; ++i)
            textures[i] = iconRegister.registerIcon("BiomesOPlenty:" + plants[i]);
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= textures.length)
            meta = 0;

        return textures[meta];
    }
    
    public int getRenderType ()
    {
        return FoliageRenderer.render;
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3)
            return 9;
        else
            return 0;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int par2, int par3, int par4)
    {
        int meta = world.getBlockMetadata(par2, par3, par4);
        
        switch (meta)
        {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
                break;
                
            case 5:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.8F, 0.7F);
                break;
                
            case 6:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.6F, 0.7F);
                break;
                
            case 11:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
                break;
                
            case 13:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
                break;
                
            case 14:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
                break;
                
            default:
                this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
                break;
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 11)
            entity.attackEntityFrom(DamageSource.cactus, 1);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < plants.length; ++i)
            list.add(new ItemStack(blockID, 1, i));
    }
    
    protected boolean canThisPlantGrowOnThisBlockID(int id)
    {
        return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID || id == Block.sand.blockID || id == Blocks.hardDirt.get().blockID || id == Blocks.redRock.get().blockID;
    }
    
    protected boolean canThisPlantGrowOnThisBlockID(int id, int metadata)
    {
		if (metadata == 6) //Tulip
    		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID || id == Blocks.holyGrass.get().blockID;
    	if (metadata == 10) //Toadstool
    		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID || id == Blocks.holyGrass.get().blockID;
    	if (metadata == 11) //Cactus
    		return id == Block.sand.blockID || id == Blocks.redRock.get().blockID;
    	if (metadata == 12) //Yucca
    		return id == Blocks.hardDirt.get().blockID || id == Blocks.redRock.get().blockID || id == Block.sand.blockID;
		if (metadata == 13) //Portobello
    		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID | id == Blocks.holyGrass.get().blockID;
		if (metadata == 14) //Blue Milk Cap
    		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID || id == Blocks.holyGrass.get().blockID;
    	else
    		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side, ItemStack itemStack)
    {
        int id = world.getBlockId(x, y - 1, z);
        int meta = itemStack.getItemDamage();
        //boolean sky = world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z);
        
        if (itemStack.itemID == this.blockID)
            switch (meta)
            {
				case 6: // Tulip
                    return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID || id == Blocks.holyGrass.get().blockID;
					
                case 10: // Toadstool
                    return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID || id == Blocks.holyGrass.get().blockID;
                    
                case 11: // Cactus
                    return id == Blocks.redRock.get().blockID || id == Block.sand.blockID;
					
				case 12: // Yucca
                    return id == Blocks.hardDirt.get().blockID || id == Blocks.redRock.get().blockID || id == Block.sand.blockID;
					
				case 13: // Portobello
                    return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID || id == Blocks.holyGrass.get().blockID;
					
				case 14: // Blue Milk Cap
                    return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.mycelium.blockID || id == Blocks.holyGrass.get().blockID;

                default:
                    return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID;
            }
        else
            return this.canPlaceBlockOnSide(world, x, y, z, side);
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        if (world.getBlockId(x, y, z) != this.blockID)
            return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) 
                    && this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
        else
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) 
                && this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z), world.getBlockMetadata(x, y, z));
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta & 15;
    }
}
