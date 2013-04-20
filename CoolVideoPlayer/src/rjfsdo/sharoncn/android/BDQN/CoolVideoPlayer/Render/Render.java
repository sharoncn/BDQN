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
	
	//立方体顶点数据
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
	
	//环境光
	private FloatBuffer lightAmbient = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	//漫射光
	private FloatBuffer lightDiffuse = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	//环境光
	private FloatBuffer lightPosition = BufferUtils.floatBuffer(new float[]{
			1.0f,1.0f,1.0f,1.0f
	});
	
	private float rotx,roty,rotz;
	public void onDrawFrame(GL10 gl) {
		//清除屏幕
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		//重置模型矩阵
		gl.glLoadIdentity();
		//开启顶点坐标功能
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		//设置顶点坐标
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cube);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
		//保存matrix当前状态
		gl.glPushMatrix();
		//向屏幕移入5个单位
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		//设置旋转轴,以x轴旋转
		gl.glRotatef(rotx, 1, 0, 0);
		//设置旋转轴,以y轴旋转
		gl.glRotatef(roty, 0, 1, 0);
		//设置旋转轴,以z轴旋转
		gl.glRotatef(rotz, 0, 0, 1);
		//绘制第一个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[0]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		//绘制第二个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[1]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
		//绘制第三个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[2]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
		//绘制第四个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
		//绘制第五个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[4]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
		//绘制第六个立方体面
		//绑定纹理号到纹理目标
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[5]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
		//将matrix回复成上面push时的 matrix
		gl.glPopMatrix();
		//关闭设置顶点功能
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		//使旋转角度不断变化
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
		//设置视口
		gl.glViewport(0, 0, width, height);
		//计算高宽比
		float ratio = (float)width / (float)height;
		//设置当前操作矩阵为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//重置矩阵
		gl.glLoadIdentity();
		//设置窗口比例和透视图,实际是生成一个投影矩阵
		GLU.gluPerspective(gl, 45.0f, ratio, 0.1f, 100.0f);
		//选择模型矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//打开光源
		gl.glEnable(GL10.GL_LIGHTING);
		//设置光源
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//初始化立方体坐标和纹理
		cube = BufferUtils.floatBuffer(cubeData);
		texture = BufferUtils.floatBuffer(textData);
		//开启纹理功能
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//加载纹理
		loadBitmapTex(gl,new int[]{R.raw.opengl3d,R.raw.opengl02,R.raw.opengl03,R.raw.opengl04,R.raw.opengl05,R.raw.opengl06});

		//平滑着色
		gl.glShadeModel(GL10.GL_SMOOTH);
		//设置黑色背景
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		//设置深度缓存
		gl.glClearDepthf(1.0f);
		//启动深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		//深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
		//告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		//启用混合
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
	}

	private void loadBitmapTex(GL10 gl,int[] res)
	{
		//将图片资源转换为位图资源
		Bitmap bmp = null;
		//初始化res.length个长度的数组
		mTextureID = new int[res.length];
		//将纹理号保存到mTextureID
		gl.glGenTextures(6, mTextureID,0);
		for(int i = 0;i < res.length;i++){
			bmp = Utils.getTextureFromBitmapResource(context, res[i]);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID[i]);
			//创建纹理
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
			//设定纹理过滤器
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		}
		
		bmp.recycle();
	}
}
