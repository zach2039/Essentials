package com.Da_Technomancer.essentials.blocks;

import com.Da_Technomancer.essentials.Essentials;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ObjectHolder;

public class ItemSplitterTileEntity extends BasicItemSplitterTileEntity{

	@ObjectHolder(registryName="block_entity_type", value=Essentials.MODID + ":item_splitter")
	public static BlockEntityType<ItemSplitterTileEntity> TYPE = null;

	public int redstone;

	public ItemSplitterTileEntity(BlockPos pos, BlockState state){
		super(TYPE, pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag nbt){
		super.saveAdditional(nbt);
		nbt.putInt("reds", redstone);
	}

	@Override
	public void load(CompoundTag nbt){
		super.load(nbt);
		redstone = nbt.getInt("reds");
	}

	@Override
	public SplitDistribution getDistribution(){
		return SplitDistribution.FIFTEEN;
	}

	@Override
	public int getMode(){
		return redstone;
	}
} 
