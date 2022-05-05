package site.wetsion.framework.mitosis.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Slf4j
public class ShellUtil {
    private static ExecutorService executor = Executors.newFixedThreadPool(32);
    private static final int SUCCESS = 0;
    public static final int EXCEPTION_RESULT = Integer.MIN_VALUE;


    /**
     * 异步用于清空调用外部命令相关流
     */
    public static class ClearProcessBufferTask implements Callable<String> {
        private InputStream inputStream;
        private String processName;

        public ClearProcessBufferTask(InputStream inputStream, String processName) {
            this.inputStream = inputStream;
            this.processName = processName;
        }


        @Override
        public String call() {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                    log.info(processName + " => " + line);
                }
                return sb.toString();
            } catch (Exception e) {
                log.error(processName + "", e);
                throw new RuntimeException(e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        log.error("ClearProcessBufferTask close BufferedReader error", e);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error("ClearProcessBufferTask close InputStream error", e);
                    }
                }
            }

        }
    }

    public static int exec(String command) {
        Process ps = null;
        try {
            long begin = System.currentTimeMillis();
            ps = Runtime.getRuntime().exec(command);
            //ps.getOutputStream();
            executor.submit(new ClearProcessBufferTask(ps.getInputStream(), "input stream "));
            executor.submit(new ClearProcessBufferTask(ps.getErrorStream(), "error stream"));
            int exitValue = ps.waitFor();
            log.info("exec command:{}, reulst:{}, cost time: {} ms", command, exitValue,
                    (System.currentTimeMillis() - begin));
            return exitValue;
        } catch (InterruptedException e) {
            log.error("exec shell Interrupted, command = {}", command, e);
        } catch (IOException e) {
            log.error("exec shell IOException, command = {}", command, e);
        } finally {
            if (ps != null) {
                ps.destroy();
            }
        }
        return EXCEPTION_RESULT;
    }
}
