package pl.java.scalatech.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.zaxxer.hikari.pool.HikariPoolMBean;

public class HikariPoolJmx implements HikariPoolMBean {
    private final ObjectName poolAccessor;
    private final MBeanServer mBeanServer;
    private final String poolName;

    public HikariPoolJmx(final String poolName) {
        this.poolName = poolName;
        try {
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
            poolAccessor = new ObjectName("com.zaxxer.hikari:type=Pool (" + poolName + ")");
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Pool " + poolName + " could not be found", e);
        }
    }

    public String getPoolName() {
        return poolName;
    }

    @Override
    public int getIdleConnections() {
        return getCount("IdleConnections");
    }

    @Override
    public int getActiveConnections() {
        return getCount("ActiveConnections");
    }

    @Override
    public int getTotalConnections() {
        return getCount("TotalConnections");
    }

    @Override
    public int getThreadsAwaitingConnection() {
        return getCount("ThreadsAwaitingConnection");
    }

    protected int getCount(String attributeName) {
        try {
            return (Integer) mBeanServer.getAttribute(poolAccessor, attributeName);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void softEvictConnections() {
        try {
            mBeanServer.invoke(poolAccessor, "softEvictConnections", null, null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
