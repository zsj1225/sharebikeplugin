package com.jun.bikeplugin;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

public class BikeAccessibilityService extends AccessibilityService {

    private static final String MSG = "BikeAccessibilit";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(MSG,"onAccessibilityEvent");
        if(event.getSource()== null){
            return;
        }
//       event.getSource().findAccessibilityNodeInfosByText("立即安装");
        List<AccessibilityNodeInfo> carnoList = event.getSource().findAccessibilityNodeInfosByViewId("so.ofo.labofo:id/carno");
        if(carnoList != null && carnoList.size()>0){
            AccessibilityNodeInfo accessibilityNodeInfo = carnoList.get(0);
            String carno = accessibilityNodeInfo.getText().toString();
            Log.d(MSG,"onAccessibilityEvent carno:" + carno);
            handleQueryAction(carno);
            return;
        }

        List<AccessibilityNodeInfo> pwdLlList = event.getSource().findAccessibilityNodeInfosByViewId("so.ofo.labofo:id/ll_unlock_pwd");
        if(pwdLlList != null && pwdLlList.size()>0){        //进入了解锁密码界面
            AccessibilityNodeInfo accessibilityNodeInfo = pwdLlList.get(0);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<accessibilityNodeInfo.getChildCount();i++){
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                sb.append(child.getText());
            }

            List<AccessibilityNodeInfo> bikecodeLlList = event.getSource().findAccessibilityNodeInfosByViewId("so.ofo.labofo:id/unlocking_bike_code");
            if(bikecodeLlList != null && bikecodeLlList.size()>0){
                AccessibilityNodeInfo bikecodeAccessibilityNodeInfo = bikecodeLlList.get(0);
                handleInsertAction(bikecodeAccessibilityNodeInfo.getText().toString(),sb.toString());
            }

        }

    }

    /**
     * 插入密码到数据库
     * @param carno
     * @param pwd
     */
    private void handleInsertAction(String carno, String pwd) {
        Log.d(MSG,"onAccessibilityEvent handleInsertAction:carno==>" + carno + ";;;pwd==>" +pwd);
        String qResult = DbUtils.queryPwd(this, carno);
        if(qResult != null && qResult.equals(pwd)){
            Utils.showToast(this,"密码已经存在了");
        }else {
            boolean success = DbUtils.savePwd(this, carno, pwd);
            if (success){
                Utils.showToast(this,"保存成功");
            }else {
                Utils.showToast(this,"保存失败");
            }
        }
    }

    /**
     * 进入了车辆号查询逻辑
     * @param carno
     */
    private void handleQueryAction(String carno) {
        Log.d(MSG,"onAccessibilityEvent handleQueryAction:" + carno);
        String qResult = DbUtils.queryPwd(this, carno);
        if(qResult == null){
            Utils.showToast(this,"本地没有密码");
        }else {
            Toast.makeText(this,"密码是:" + qResult,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(MSG,"onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        Log.d(MSG,"onServiceConnected");
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(MSG,"onUnbind");
        return super.onUnbind(intent);
    }
}
