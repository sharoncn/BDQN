package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Render;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Utils.BufferUtils;
import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Utils.Utils;

import cn.com.jbit.coolvideoplayer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class Render implements Renderer{
	private FloatBuffer cube = null;
	private FloatBuffer texture = null;
	private Context context;
	private int[] mTextureID;
	private boolean light = true;
	
	public boolean getLight() {
		return light;
	}

	public void setLight(boolean light) {
		this.light = light;
	}

	public Render(Context context){
		this.context = context;
	}
	
	//�����嶥������
	private float[] cubeData = new float[]{
			// FRONT
            -0.5f, -0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
            // BACK
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
            // LEFT
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            // RIGHT
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
            // TOP
            -0.5f,  0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
             -0.5f,  0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
            // BOTTOM
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f, -0.5f, -0.5f
	};
	
	private float[] textData = new float[]{
			// FRONT
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
           // BACK
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f, 1.0f,
           // LEFT
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f, 1.0f,
           // RIGHT
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f, 1.0f,
           // TOP
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
           // BOTTOM
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f, 1.0f
	};
	
	//������
	private FloatBuffer lightAmbient = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	//�����
	private FloatBuffer lightDiffuse = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	//������
	private FloatBuffer lightPosition = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	
	private float rotx,roty,rotz;
	public void onDrawFrame(GL10 gl) {
		//�����Ļ
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		//����ģ�;���
		gl.glLoadIdentity();
		//�����������깦��
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		//���ö�������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cube);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
		//����matrix��ǰ״̬
		gl.glPushMatrix();
		//����Ļ����5����λ
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		//������ת��,��x����ת
		gl.glRotatef(rotx, 1, 0, 0);
		//������ת��,��y����ת
		gl.glRotatef(roty, 0, 1, 0);
		//������ת��,��z����ת
		gl.glRotatef(rotz, 0, 0, 1);
		//���Ƶ�һ����������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[0]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		//���Ƶڶ�����������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[1]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
		//���Ƶ�������������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[2]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
		//���Ƶ��ĸ���������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
		//���Ƶ������������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[4]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
		//���Ƶ�������������
		//������ŵ�����Ŀ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[5]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
		//��matrix�ظ�������pushʱ�� matrix
		gl.glPopMatrix();
		//�ر����ö��㹦��
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		//ʹ��ת�ǶȲ��ϱ仯
		rotx+=0.2f;
		roty+=0.3f;
		rotz+=0.4f;
		if(light){
			gl.glEnable(GL10.GL_LIGHT1);
		}else{
			gl.glDisable(GL10.GL_LIGHT1);
		}
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//�����ӿ�
		gl.glViewport(0, 0, width, height);
		//����߿��
		float ratio = (float)width / (float)height;
		//���õ�ǰ��������ΪͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//���þ���
		gl.glLoadIdentity();
		//���ô��ڱ�����͸��ͼ,ʵ��������һ��ͶӰ����
		GLU.gluPerspective(gl, 45.0f, ratio, 0.1f, 100.0f);
		//ѡ��ģ�;���
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//�򿪹�Դ
		gl.glEnable(GL10.GL_LIGHTING);
		//���ù�Դ
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//��ʼ�����������������
		cube = BufferUtils.floatBuffer(cubeData);
		texture = BufferUtils.floatBuffer(textData);
		//����������
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//��������
		loadBitmapTex(gl,new int[]{R.raw.opengl3d,R.raw.opengl02,R.raw.opengl03,R.raw.opengl04,R.raw.opengl05,R.raw.opengl06});

		//ƽ����ɫ
		gl.glShadeModel(GL10.GL_SMOOTH);
		//���ú�ɫ����
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		//������Ȼ���
		gl.glClearDepthf(1.0f);
		//������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		//��Ȳ��Ե�����
		gl.glDepthFunc(GL10.GL_LEQUAL);
		//����ϵͳ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		//���û��
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
	}

	private void loadBitmapTex(GL10 gl,int[] res)
	{
		//��ͼƬ��Դת��Ϊλͼ��Դ
		Bitmap bmp = null;
		//��ʼ��res.length�����ȵ�����
		mTextureID = new int[res.length];
		//������ű��浽mTextureID
		gl.glGenTextures(6, mTextureID,0);
		for(int i = 0;i < res.length;i++){
			bmp = Utils.getTextureFromBitmapResource(context, res[i]);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[i]);
			//��������
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
			//�趨���������
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		}
		
		bmp.recycle();
	}
}
