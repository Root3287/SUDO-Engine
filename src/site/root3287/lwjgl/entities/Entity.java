package site.root3287.lwjgl.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import site.root3287.lwjgl.component.Component;

public abstract class Entity {
	protected HashMap<Class, HashMap<UUID, ? extends Component>> componentStores = new HashMap<Class, HashMap<UUID,? extends Component>>();
	public static List<UUID> allEntities = new ArrayList<>();
	protected UUID id;
	
	protected Entity(){
		this.id = UUID.randomUUID();
		allEntities.add(this.id);
	}
	
	public abstract void update(float delta);
	
	public <T extends Component> void addComponent(UUID entity, T component){

		synchronized (componentStores){
			HashMap<UUID, ? extends Component> store = componentStores.get(component.getClass());

			if (store == null){
				store = new HashMap<UUID, T>();
				componentStores.put(component.getClass(), store);
			}

			((HashMap<UUID, T>) store).put(entity, component);
		}
	}
	public <T extends Component> void addComponent(T component){

		synchronized (componentStores){
			HashMap<UUID, ? extends Component> store = componentStores.get(component.getClass());

			if (store == null){
				store = new HashMap<UUID, T>();
				componentStores.put(component.getClass(), store);
			}

			((HashMap<UUID, T>) store).put(this.id, component);
		}
	}
	public <T> T getComponent(UUID e, Class<T> exampleClass ){
		HashMap<UUID, ? extends Component> store = componentStores.get( exampleClass );
		T result = (T) store.get(e);
		if( result == null )
			throw new IllegalArgumentException("GET FAIL: "+e+" does not possess Component of class\n missing: "+exampleClass);
		return result;
	}
	
	public <T> T getComponent(Class<T> exampleClass){ 
		HashMap<UUID, ? extends Component> store = componentStores.get(exampleClass);
		T result = (T) store.get(this.id);
		if(result == null)
			throw new IllegalArgumentException("Get Fail: "+this.id.toString()+" does not posses Component of Class \n missing: "+ exampleClass);
		return result;
	}
}
