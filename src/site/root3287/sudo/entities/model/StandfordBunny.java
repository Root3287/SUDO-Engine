package site.root3287.sudo.entities.model;

import site.root3287.sudo.component.ModelComponent;
import site.root3287.sudo.component.SoundComponent;
import site.root3287.sudo.component.TransformationComponent;
import site.root3287.sudo.engine.Loader;
import site.root3287.sudo.engine.objConverter.ModelData;
import site.root3287.sudo.engine.objConverter.OBJFileLoader;
import site.root3287.sudo.entities.Entity;
import site.root3287.sudo.model.TexturedModel;
import site.root3287.sudo.texture.ModelTexture;

public class StandfordBunny extends Entity{
	private Loader loader;
	public StandfordBunny(Loader loader) {
		this.loader = loader;
		ModelData data = OBJFileLoader.loadOBJ("res/model/standfordBunny/bunny.obj");
		ModelComponent model = new ModelComponent(
				new TexturedModel(
						loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()), 
						new ModelTexture(loader.loadTexture("res/image/white.png"))));
		TransformationComponent transform = new TransformationComponent();
		SoundComponent sounds = new SoundComponent(this.id,"JUMP", "sounds/Select.wav");
		sounds.player.setPosition(transform.position);
		sounds.player.setAttenuation(5, 30, 10);
		sounds.player.setLooping(true);
		sounds.player.play(sounds.sounds.get("JUMP"));
		addComponent(model);
		addComponent(transform);
		addComponent(sounds);
	}
	
	@Override
	public void update(float delta) {
		
	}

	public void dispose(){
		this.getComponent(SoundComponent.class).dispose();
	}

}
