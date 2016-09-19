package com.tripint.intersight.activity.base;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * 类描述：Activity管理 修改时间：2015年5月17日 下午3:12:51 修改备注：
 */
public class ActivityManager {
    private static ActivityManager instance;
    private Stack<FragmentActivity> fragmentActivityStack;// fragmentActivity栈
    private Stack<AppCompatActivity> activityStack;// activity栈
    private Stack<AppCompatActivity> activityHomeStack;// activity栈

    private ActivityManager() {
    }

    // 单例模式
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    // 把一个activity压入栈中
    public void addActivity(AppCompatActivity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(actvity);
    }

    // 把一个activity压入栈中
    public void addActivityHome(AppCompatActivity actvity) {
        if (activityHomeStack == null) {
            activityHomeStack = new Stack<>();
        }
        activityHomeStack.add(actvity);
    }

    // 把一个fragmentActivity压入栈中
    public void addActivity(FragmentActivity actvity) {
        if (fragmentActivityStack == null) {
            fragmentActivityStack = new Stack<>();
        }
        fragmentActivityStack.add(actvity);
    }

    // 获取栈顶的activity，先进后出原则
    public AppCompatActivity getLastActivity() {
        return activityStack.lastElement();
    }

    // 获取栈顶的activity，先进后出原则
    public AppCompatActivity getLastHomeActivity() {
        return activityHomeStack.lastElement();
    }

    // 获取栈顶的fragmentActivity，先进后出原则
    public FragmentActivity getLastActivityfragment() {
        return fragmentActivityStack.lastElement();
    }

    // 保留一个指定的，其它全移除
    public void popOneActivity(AppCompatActivity activity) {
        if (fragmentActivityStack != null) {
            while (fragmentActivityStack.size() > 0) {
                FragmentActivity _activity = getLastActivityfragment();
                if (_activity.equals(activity) || _activity == activity) {
                    break;
                }
                if (activity == null) {
                    break;
                }
                removerActivity(_activity);
            }
        }

        if (activityStack != null) {
            while (activityStack.size() > 0) {
                AppCompatActivity _activity = getLastActivity();
                if (_activity.equals(activity) || _activity == activity) {
                    break;
                }
                if (activity == null) {
                    break;
                }
                removerActivity(_activity);
            }
        }
    }

    // 保留一个指定的，其它全移除
    public void popOneActivity(FragmentActivity activity) {
        if (fragmentActivityStack != null) {
            while (fragmentActivityStack.size() > 0) {
                FragmentActivity _activity = getLastActivityfragment();
                if (_activity.equals(activity) || _activity == activity) {
                    break;
                }
                if (activity == null) {
                    break;
                }
                removerActivity(_activity);
            }
        }

        if (activityStack != null) {
            while (activityStack.size() > 0) {
                AppCompatActivity _activity = getLastActivity();
                if (_activity.equals(activity) || _activity == activity) {
                    break;
                }
                if (activity == null) {
                    break;
                }
                removerActivity(_activity);
            }
        }
    }

    // 移除一个activity
    public void removerActivity(FragmentActivity activity) {
        if (fragmentActivityStack != null && fragmentActivityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                fragmentActivityStack.remove(activity);
            }
        }
    }

    // 移除一个activity
    public void removerActivity(AppCompatActivity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    // 移除一个activity
    public void removerHomeActivity(AppCompatActivity activity) {
        if (activityHomeStack != null && activityHomeStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityHomeStack.remove(activity);
            }
        }
    }

    // 退出所有activity
    public void finishAllActivity() {
//        if (fragmentActivityStack != null) {
//            while (fragmentActivityStack.size() > 0) {
//                FragmentActivity activity = getLastActivityfragment();
//                if (activity == null) {
//                    break;
//                }
//                removerActivity(activity);
//            }
//        }
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                AppCompatActivity activity = getLastActivity();
                if (activity == null) {
                    break;
                }
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    // 退出首页
    public void finishHomeActivity() {
        if (activityHomeStack != null) {
            while (activityHomeStack.size() > 0) {
                AppCompatActivity activity = getLastHomeActivity();
                if (activity == null) {
                    break;
                }
                activity.finish();
                activityHomeStack.remove(activity);
            }
        }
    }

    /**
     * 根据activity的 名字获得activity的实例
     */
    @SuppressWarnings("unchecked")
    public <T extends AppCompatActivity> T getActivity(Class<T> cls) {
        AppCompatActivity act = null;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            act = activityStack.get(i);
            if (act.getClass() == cls) {
                return (T) act;
            }
        }
        return null;
    }

    /**
     * 根据FragmentActivity的 名字获得FragmentActivity的实例
     */
    @SuppressWarnings("unchecked")
    public <T extends FragmentActivity> T getFragmentActivity(Class<T> cls) {
        Activity act = null;
        for (int i = 0, size = fragmentActivityStack.size(); i < size; i++) {
            act = fragmentActivityStack.get(i);
            if (act.getClass() == cls) {
                return (T) act;
            }
        }
        return null;
    }

    public void appExit() {
        finishAllActivity();
        finishHomeActivity();
        System.gc();
        System.exit(0);
    }

    public void appExitNoGc() {
        finishAllActivity();
        finishHomeActivity();
    }

    public void clear() {
        if (null != fragmentActivityStack) {
            fragmentActivityStack.clear();
        }
        if (null != activityStack) {
            activityStack.clear();
        }
        if (null != activityHomeStack) {
            activityHomeStack.clear();
        }
        System.gc();
        System.exit(0);
    }
}
