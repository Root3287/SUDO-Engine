package site.root3287.sudo.shader.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import site.root3287.sudo.entities.Light;
import site.root3287.sudo.entities.Camera.Camera;
import site.root3287.sudo.shader.Shader;

public class TerrainShader extends Shader{
	private static final String VERTEX_FILE = "res/shaders/terrain/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "res/shaders/terrain/terrainFragmentShader.glsl";
     
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_fogDensity;
    private int location_fogGradient;
    private int location_skyColour;
    private int location_useFakeLight;
    
    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogGradient = super.getUniformLocation("fogGradient");
        location_skyColour = super.getUniformLocation("skyColour");
        location_useFakeLight = super.getUniformLocation("useFakeLight");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
    }
     
    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
     
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
     
    public void loadLight(Light light){
    	super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }
     
    public void loadViewMatrix(){
        Matrix4f viewMatrix = Camera.viewMatrix;
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
     
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    public void setFog(float fogDensity, float fogGradient){
    	super.loadFloat(location_fogDensity, fogDensity);
    	super.loadFloat(location_fogGradient, fogGradient);
    }
    public void loadSkyColour(float r, float g, float b){
    	super.loadVector(location_skyColour, new Vector3f(r,g,b));
    }
    public void loadFakeLight(boolean fakeLight){
    	float useFakeLight = 0.0f;
    	if(fakeLight){
    		useFakeLight = 1.0f;
    	}
    	super.loadFloat(location_useFakeLight, useFakeLight);
    }
}
