package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.PayAccountBean;
import com.miuhouse.yourcompany.teacher.model.PayAccountListBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.PayTypeAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kings on 7/21/2016.
 */
public class ChooseWithdrawAccountActivity extends BaseActivity {

    public final static int REQUEST = 1;
    private Button btnAddWithdrawAccount;
    private ListView listPayAccount;
    private PayTypeAdapter adapter;
    private List<PayAccountBean> payAccountList = new ArrayList<>();

    private String accounId;

    @Override
    protected String setTitle() {
        return "选择账户";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {
        btnAddWithdrawAccount = (Button) findViewById(R.id.btn_add_withdrawaccount);
        listPayAccount = (ListView) findViewById(R.id.recycler_pay_type);
        listPayAccount.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new PayTypeAdapter(this, payAccountList);
        listPayAccount.setAdapter(adapter);
        listPayAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accounId = payAccountList.get(position).getId();
            }
        });

        btnAddWithdrawAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, AddWithdrawAccountActivity.class), REQUEST);
            }
        });
        sendReques();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_withdraw_account;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    private void sendReques() {
        String urlPath = Constants.URL_VALUE + "accountList";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), PayAccountListBean.class, new Response.Listener<PayAccountListBean>() {
            @Override
            public void onResponse(PayAccountListBean response) {
                if (payAccountList.size() > 0)
                    payAccountList.clear();

                for (int i = 0; i < response.getAccounts().size(); i++) {
                    if (response.getAccounts().get(i).getIsDefault().equals("1")) {
                        listPayAccount.setItemChecked(i, true);
                    }
                }
                payAccountList.addAll(response.getAccounts());
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            L.i("TAG");
            sendReques();
        }
    }

    @Override
    public void onRightClick() {
        if (accounId != null) {
            sendRequest();
        }
    }

    public void sendRequest() {
        String urlPath = Constants.URL_VALUE + "accountDefault";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("id", accounId);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Toast.makeText(context, response.getMsg(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
