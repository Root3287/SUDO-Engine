package site.root3287.lwjgl.component;

import java.util.UUID;

import site.root3287.lwjgl.audio.Audio;
import site.root3287.lwjgl.entities.Entity;

public class AudioListenerComponent extends Component{
	private UUID id; 
	public AudioListenerComponent(UUID id) {
		this.id = id;
		if(!Entity.hasComponent(id, TransformationComponent.class))
			throw new IllegalAccessError("You cannot start an audio listener with out an Transformation Component!");
	}
	public void update(float delta){
		Audio.setListenerData(Entity.getComponent(id, TransformationComponent.class).position);
	}
}
