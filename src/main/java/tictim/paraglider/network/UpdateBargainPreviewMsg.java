package tictim.paraglider.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tictim.paraglider.recipe.bargain.StatueBargainContainer;
import tictim.paraglider.recipe.bargain.StatueBargainContainer.ItemDemand;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class UpdateBargainPreviewMsg{
	public static UpdateBargainPreviewMsg read(FriendlyByteBuf buf){
		UpdateBargainPreviewMsg msg = new UpdateBargainPreviewMsg();
		int size = buf.readUnsignedByte();
		for(int i = 0; i<size; i++) msg.add(buf.readResourceLocation(), Data.readData(buf));
		return msg;
	}

	private final Map<ResourceLocation, Data> updated = new HashMap<>();

	public void add(ResourceLocation key, StatueBargainContainer.Preview preview, boolean includeItemDemands){
		if(updated.size()>=256) throw new IllegalArgumentException("Too many recipes have been written in single packet");
		updated.put(key, new Data(preview.canBargain(), includeItemDemands ? preview.getDemands() : null));
	}
	public void add(ResourceLocation key, Data data){
		updated.put(key, data);
	}

	public Map<ResourceLocation, Data> getUpdated(){
		return updated;
	}

	public void write(FriendlyByteBuf buf){
		buf.writeByte(updated.size());
		for(Map.Entry<ResourceLocation, Data> e : updated.entrySet()){
			buf.writeResourceLocation(e.getKey());
			e.getValue().write(buf);
		}
	}

	public record Data(boolean canBargain, @Nullable ItemDemand[] demands){
		private static Data readData(FriendlyByteBuf buf){
			boolean canBargain = buf.readBoolean();
			ItemDemand[] demands;
			if(buf.readBoolean()){
				demands = new ItemDemand[buf.readVarInt()];
				for(int i = 0; i<demands.length; i++){
					demands[i] = readItemDemand(buf);
				}
			}else demands = null;
			return new Data(canBargain, demands);
		}
		private static ItemDemand readItemDemand(FriendlyByteBuf buf){
			ItemStack[] previewItems = new ItemStack[buf.readVarInt()];
			for(int i = 0; i<previewItems.length; i++){
				previewItems[i] = buf.readItem();
			}
			int quantity = buf.readVarInt();
			int count = buf.readVarInt();
			return new ItemDemand(previewItems, quantity, count);
		}
		private static void writeItemDemand(ItemDemand itemDemand, FriendlyByteBuf buf){
			buf.writeVarInt(itemDemand.getPreviewItems().length);
			for(ItemStack s : itemDemand.getPreviewItems()) buf.writeItem(s);
			buf.writeVarInt(itemDemand.getQuantity());
			buf.writeVarInt(itemDemand.getCount());
		}

		public void write(FriendlyByteBuf buf){
			buf.writeBoolean(canBargain);
			buf.writeBoolean(demands!=null);
			if(demands!=null){
				buf.writeVarInt(demands.length);
				for(ItemDemand d : demands)
					writeItemDemand(d, buf);
			}
		}
	}
}
