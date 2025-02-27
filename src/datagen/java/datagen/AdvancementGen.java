package datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import tictim.paraglider.contents.Contents;
import tictim.paraglider.contents.ModAdvancements;
import tictim.paraglider.contents.ModTags;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static tictim.paraglider.ParagliderMod.MODID;

public class AdvancementGen extends ModAdvancementProvider{
	public AdvancementGen(DataGenerator generator, ExistingFileHelper existingFileHelper){
		super(generator, existingFileHelper);
	}

	@Override protected void registerAdvancements(Consumer<Advancement> consumer){
		Advancement root = advancement(
				new ItemStack(Contents.PARAGLIDER.get()),
				"advancement.paraglider",
				new ResourceLocation(MODID, "textures/gui/advancement_background.png"),
				FrameType.TASK,
				false,
				false,
				false)
				.addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE))
				.save(consumer, MODID+":root");
		Advancement paraglider = advancement(
				new ItemStack(Contents.PARAGLIDER.get()),
				"advancement.paraglider.paraglider",
				FrameType.GOAL,
				true,
				true,
				false)
				.parent(root)
				.addCriterion("paraglider", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.PARAGLIDERS).build()))
				.save(consumer, MODID+":paraglider");
		Advancement prayToTheGoddess = advancement(
				new ItemStack(Contents.GODDESS_STATUE.get()),
				"advancement.paraglider.pray_to_the_goddess",
				FrameType.GOAL,
				true,
				true,
				false)
				.parent(root)
				.addCriterion("bargain", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, ModAdvancements.PRAY_TO_THE_GODDESS.toString());
		Advancement statuesBargain = advancement(
				new ItemStack(Contents.HORNED_STATUE.get()),
				"advancement.paraglider.statues_bargain",
				FrameType.GOAL,
				true,
				true,
				false)
				.parent(root)
				.addCriterion("bargain", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, ModAdvancements.STATUES_BARGAIN.toString());
		Advancement allVessels = advancement(
				new ItemStack(Contents.HEART_CONTAINER.get()),
				"advancement.paraglider.all_vessels",
				FrameType.CHALLENGE,
				true,
				true,
				false)
				.parent(root)
				.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, ModAdvancements.ALL_VESSELS.toString());
	}

	private static Advancement.Builder advancement(ItemStack stack,
	                                               String display,
	                                               FrameType frameType,
	                                               boolean showToast,
	                                               boolean announceToChat,
	                                               boolean hidden){
		return advancement(stack, display, null, frameType, showToast, announceToChat, hidden);
	}
	private static Advancement.Builder advancement(ItemStack stack,
	                                               String display,
	                                               @Nullable ResourceLocation background,
	                                               FrameType frameType,
	                                               boolean showToast,
	                                               boolean announceToChat,
	                                               boolean hidden){
		return Advancement.Builder.advancement().display(stack,
				new TranslatableComponent(display),
				new TranslatableComponent(display+".desc"),
				background,
				frameType,
				showToast,
				announceToChat,
				hidden);
	}
}
