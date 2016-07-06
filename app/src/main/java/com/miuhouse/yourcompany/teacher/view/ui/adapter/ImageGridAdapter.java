package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by kings on 7/5/2016.
 */
public class ImageGridAdapter extends BaseAdapter {
    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;
    private List<Image> list = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();

    private Context mContext;
    private LayoutInflater mInflater;
    //是否显示camer
    private boolean isShowCamera;
    private boolean showSelectIndicator = true;

    private int mItemSize;
    private GridView.LayoutParams mItemLayoutParams;

    public ImageGridAdapter(Context mContext, boolean isShowCamer) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.isShowCamera = isShowCamer;
        mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }
    public void setShowSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (isShowCamera == b)
            return;
        isShowCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     */
    public void select(Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        } else {
            mSelectedImages.add(image);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
        if (mSelectedImages.size() > 0) {
            notifyDataSetChanged();
        }
    }

    private Image getImageByPath(String path) {
        if (list != null && list.size() > 0) {
            for (Image image : list) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     *
     * @param images
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();
        if (images != null && images.size() > 0) {
            list = images;
        } else {
            list.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 重置每一个Column的size
     *
     * @param columnWidth
     */
    public void setItemSize(int columnWidth) {
        if (mItemSize == columnWidth) {
            return;
        }
        mItemSize = columnWidth;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        if (isShowCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == TYPE_CAMERA) {
            convertView = mInflater.inflate(R.layout.list_item_camera, null);
            convertView.setTag(null);
        } else {
            ViewHolde viewHolde = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_image, null);
                viewHolde = new ViewHolde(convertView);
                convertView.setTag(viewHolde);
            } else {
                viewHolde = (ViewHolde) convertView.getTag();
                if (viewHolde == null) {
                    convertView = mInflater.inflate(R.layout.list_item_image, parent, false);
                    viewHolde = new ViewHolde(convertView);
                }
            }
        }
        return null;
    }

    class ViewHolde {
        ImageView image;
        ImageView indicator;
        View mask;

        ViewHolde(View view) {
            image = (ImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
        }

        void bindData(final Image data) {
            if (data == null)
                return;
            if (showSelectIndicator) {
                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImages.contains(data)) {
                    //设置选中状态
                    indicator.setImageResource(R.mipmap.btn_selected);
                    mask.setVisibility(View.VISIBLE);
                } else {
                    indicator.setImageResource(R.mipmap.btn_unselected);
                    mask.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            File imageFile = new File(data.path);
            if (mItemSize > 0) {
                Glide.with(mContext).load(imageFile).override(mItemSize, mItemSize).centerCrop().into(image);
            }
        }
    }
}
