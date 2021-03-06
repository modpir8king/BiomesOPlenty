package biomesoplenty.items;

import biomesoplenty.BiomesOPlenty;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;

public class ItemShroomPowder extends ItemFood
{
	public ItemShroomPowder(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
		setPotionEffect(Potion.confusion.id, 30, 0, 0.6F);
		setAlwaysEdible().setUnlocalizedName("shroomPowder");
		setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}
	
	public void registerIcons(IconRegister iconRegister)
	{
    	itemIcon = iconRegister.registerIcon("BiomesOPlenty:shroompowder");
	}
}
