package at.jojokobi.pokemine.trainer.entity;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.CustomEntityType;
import at.jojokobi.mcutil.entity.EntityHandler;

public abstract class NPCEntity extends CustomEntity<ArmorStand>{
	
	public NPCEntity(Location place, EntityHandler handler, CustomEntityType<?> type) {
		super(place, handler, type);
	}
	
	@Override
	public void loop() {
		super.loop();
		if (getEntity() != null) {
			getEntity().setCustomName(getName());
		}
	}
	
	@Override
	protected ArmorStand createEntity(Location place) {
		ArmorStand stand = (ArmorStand) place.getWorld().spawnEntity(place, EntityType.ARMOR_STAND);
		stand.setVisible(false);
		stand.getEquipment().setHelmet(getItem());
		stand.setCustomName(getName());
		stand.setCustomNameVisible(true);
		stand.setCanPickupItems(false);
		place.getChunk().load();
		return stand;
	}
	
	public boolean onInteract (Player player) {
		return false;
	}
	
	public abstract ItemStack getItem ();

	public abstract String getName();

}
