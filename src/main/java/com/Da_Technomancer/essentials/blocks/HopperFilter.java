package com.Da_Technomancer.essentials.blocks;

import com.Da_Technomancer.essentials.ESConfig;
import com.Da_Technomancer.essentials.tileentities.HopperFilterBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.Player;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockPlaceContext ;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateDefinition;
import net.minecraft.tileentity.BlockEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.AbstractBlock.Properties;

public class HopperFilter extends BaseEntityBlock{

	protected HopperFilter(){
		super(Properties.of(Material.STONE).sound(SoundType.STONE).strength(2));
		String name = "hopper_filter";
		setRegistryName(name);
		ESBlocks.toRegister.add(this);
		ESBlocks.blockAddQue(this);
	}

	@Override
	public BlockEntity newBlockEntity(IBlockReader world){
		return new HopperFilterBlockEntity();
	}

//	@Override
//	public BlockRenderLayer getRenderLayer(){
//		return BlockRenderLayer.CUTOUT;
//	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag advanced){
		tooltip.add(new TranslationTextComponent("tt.essentials.hopper_filter.desc"));
		tooltip.add(new TranslationTextComponent("tt.essentials.hopper_filter.move"));
		tooltip.add(new TranslationTextComponent("tt.essentials.hopper_filter.shulker"));
	}

	private static final VoxelShape[] BB = new VoxelShape[3];

	static{
		BB[0] = VoxelShapes.joinUnoptimized(box(0, 0, 0, 4, 16, 16), VoxelShapes.joinUnoptimized(box(12, 0, 0, 16, 16, 16), box(4, 4, 4, 12, 12, 12), IBooleanFunction.OR), IBooleanFunction.OR);//X axis
		BB[1] = VoxelShapes.joinUnoptimized(box(0, 0, 0, 16, 4, 16), VoxelShapes.joinUnoptimized(box(0, 12, 0, 16, 16, 16), box(4, 4, 4, 12, 12, 12), IBooleanFunction.OR), IBooleanFunction.OR);//Y axis
		BB[2] = VoxelShapes.joinUnoptimized(box(0, 0, 0, 16, 16, 4), VoxelShapes.joinUnoptimized(box(0, 0, 12, 16, 16, 16), box(4, 4, 4, 12, 12, 12), IBooleanFunction.OR), IBooleanFunction.OR);//Z axis
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
		return BB[state.getValue(ESProperties.AXIS).ordinal()];
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
		builder.add(ESProperties.AXIS);
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof HopperFilterBlockEntity) {
				InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((HopperFilterBlockEntity) te).getFilter());
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult hit){
		if(ESConfig.isWrench(playerIn.getItemInHand(hand))){
			if(!worldIn.isClientSide){
				worldIn.setBlockAndUpdate(pos, state.cycle(ESProperties.AXIS));//MCP note: cycle
			}
			return InteractionResult.SUCCESS;
		}else{
			BlockEntity te = worldIn.getBlockEntity(pos);
			if(te instanceof HopperFilterBlockEntity){
				if(!worldIn.isClientSide){
					HopperFilterBlockEntity fte = (HopperFilterBlockEntity) te;
					ItemStack held = playerIn.getItemInHand(hand);
					if(fte.getFilter().isEmpty() && !held.isEmpty()){
						fte.setFilter(held.split(1));
						playerIn.setItemInHand(hand, held);
					}else if(!fte.getFilter().isEmpty() && held.isEmpty()){
						playerIn.setItemInHand(hand, fte.getFilter());
						fte.setFilter(ItemStack.EMPTY);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.FAIL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext  context){
		return defaultBlockState().setValue(ESProperties.AXIS, context.getClickedFace().getAxis());
	}

	@Override
	public RenderShape getRenderShape(BlockState state){
		return RenderShape.MODEL;
	}
}
