package classes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import interfaces.FileIO;

public class AndroidFileIO implements FileIO {
	Context mContext;
	AssetManager mAssetMgr;
	String externalStroragePath;
	
	public AndroidFileIO(Context _ctx){
		mContext=_ctx;
		mAssetMgr=mContext.getAssets();
		externalStroragePath=Environment.getExternalStorageDirectory().getAbsolutePath()+ File.pathSeparator;
	}

	@Override
	public InputStream readAsset(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream readFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public SharedPreferences getPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(mContext);
	}

}
