
package finalproject.product_dimension_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: product_dimension Purpose: <br>
 * Description: <br>
 * 
 * @author R, Sahitya
 * @version 8.0.1.20240222_1049-patch
 * @status
 */
public class product_dimension implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "product_dimension.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(product_dimension.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

			if (exeID != null) {

				this.setProperty("exeID", exeID.toString());

			}

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

		public Integer exeID;

		public Integer getExeID() {
			return this.exeID;
		}
	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "product_dimension";
	private final String projectName = "FINALPROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_8GrN4OwPEe6uVJCUD8Lptw", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	LogCatcherUtils tLogCatcher_1 = new LogCatcherUtils();

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					product_dimension.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(product_dimension.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
						tLogCatcher_1.addMessage("Java Exception", currentComponent, 6,
								e.getClass().getName() + ":" + e.getMessage(), 1);
						tLogCatcher_1Process(globalMap);
					}
				} catch (TalendException e) {
					// do nothing

				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void ExecutionLogStart_1_tPrejob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tPrejob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tDBConnection_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tMap_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tLogRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tRowGenerator_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tMap_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		ExecutionLogStart_1_tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRowGenerator_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogCatcher_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tLogCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tLogCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tLogCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void ExecutionLogStart_1_tPrejob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void ExecutionLogStart_1_tDBConnection_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void ExecutionLogStart_1_tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void ExecutionLogStart_1_tRowGenerator_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRowGenerator_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tLogCatcher_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void ExecutionLogStart_1_tPrejob_1Process(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("ExecutionLogStart_1_tPrejob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "ExecutionLogStart_1_tPrejob_1");
		org.slf4j.MDC.put("_subJobPid", "MTyfQk_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [ExecutionLogStart_1_tPrejob_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tPrejob_1", false);
				start_Hash.put("ExecutionLogStart_1_tPrejob_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				int tos_count_ExecutionLogStart_1_tPrejob_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tPrejob_1", "tPrejob_1", "tPrejob");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [ExecutionLogStart_1_tPrejob_1 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tPrejob_1 main ] start
				 */

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				tos_count_ExecutionLogStart_1_tPrejob_1++;

				/**
				 * [ExecutionLogStart_1_tPrejob_1 main ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tPrejob_1 process_data_begin ] start
				 */

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				/**
				 * [ExecutionLogStart_1_tPrejob_1 process_data_begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tPrejob_1 process_data_end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				/**
				 * [ExecutionLogStart_1_tPrejob_1 process_data_end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tPrejob_1 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				ok_Hash.put("ExecutionLogStart_1_tPrejob_1", true);
				end_Hash.put("ExecutionLogStart_1_tPrejob_1", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("ExecutionLogStart_1_OnComponentOk1", 0, "ok");
				}
				ExecutionLogStart_1_tDBConnection_1Process(globalMap);

				/**
				 * [ExecutionLogStart_1_tPrejob_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [ExecutionLogStart_1_tPrejob_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tPrejob_1";

				/**
				 * [ExecutionLogStart_1_tPrejob_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("ExecutionLogStart_1_tPrejob_1_SUBPROCESS_STATE", 1);
	}

	public void ExecutionLogStart_1_tDBConnection_1Process(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("ExecutionLogStart_1_tDBConnection_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "ExecutionLogStart_1_tDBConnection_1");
		org.slf4j.MDC.put("_subJobPid", "JFtHQP_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tDBConnection_1", false);
				start_Hash.put("ExecutionLogStart_1_tDBConnection_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				int tos_count_ExecutionLogStart_1_tDBConnection_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBConnection_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tDBConnection_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tDBConnection_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("PORT" + " = " + "\"3306\"");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1
									.append("DBNAME" + " = " + "\"real_time\"");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1
									.append("USER" + " = " + "\"Revinipati\"");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:Ag35c5rDH42GwpMtdilSZ+6IwCJ3/zLyM0BYqp+BHP77bTo=")
									.substring(0, 4) + "...");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1
									.append("USE_SHARED_CONNECTION" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1
									.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append("AUTO_COMMIT" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1
									.append("UNIFIED_COMPONENTS" + " = " + "tMysqlConnection");
							log4jParamters_ExecutionLogStart_1_tDBConnection_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tDBConnection_1 - "
										+ (log4jParamters_ExecutionLogStart_1_tDBConnection_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tDBConnection_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tDBConnection_1", "tDBConnection_1", "tMysqlConnection");
					talendJobLogProcess(globalMap);
				}

				String properties_ExecutionLogStart_1_tDBConnection_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_ExecutionLogStart_1_tDBConnection_1 == null
						|| properties_ExecutionLogStart_1_tDBConnection_1.trim().length() == 0) {
					properties_ExecutionLogStart_1_tDBConnection_1 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_ExecutionLogStart_1_tDBConnection_1.contains("rewriteBatchedStatements=")) {
						properties_ExecutionLogStart_1_tDBConnection_1 += "&rewriteBatchedStatements=true";
					}

					if (!properties_ExecutionLogStart_1_tDBConnection_1.contains("allowLoadLocalInfile=")) {
						properties_ExecutionLogStart_1_tDBConnection_1 += "&allowLoadLocalInfile=true";
					}
				}

				String url_ExecutionLogStart_1_tDBConnection_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/"
						+ "real_time" + "?" + properties_ExecutionLogStart_1_tDBConnection_1;
				String dbUser_ExecutionLogStart_1_tDBConnection_1 = "Revinipati";

				final String decryptedPassword_ExecutionLogStart_1_tDBConnection_1 = routines.system.PasswordEncryptUtil
						.decryptPassword(
								"enc:routine.encryption.key.v1:jK7a0bxa/CEK7+fXKcKRMQYIISikd5dJ/5AsKK6XaMGEAz8=");
				String dbPwd_ExecutionLogStart_1_tDBConnection_1 = decryptedPassword_ExecutionLogStart_1_tDBConnection_1;

				java.sql.Connection conn_ExecutionLogStart_1_tDBConnection_1 = null;

				String driverClass_ExecutionLogStart_1_tDBConnection_1 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_ExecutionLogStart_1_tDBConnection_1 = java.lang.Class
						.forName(driverClass_ExecutionLogStart_1_tDBConnection_1);
				globalMap.put("driverClass_ExecutionLogStart_1_tDBConnection_1",
						driverClass_ExecutionLogStart_1_tDBConnection_1);

				log.debug("ExecutionLogStart_1_tDBConnection_1 - Driver ClassName: "
						+ driverClass_ExecutionLogStart_1_tDBConnection_1 + ".");

				log.debug("ExecutionLogStart_1_tDBConnection_1 - Connection attempt to '"
						+ url_ExecutionLogStart_1_tDBConnection_1 + "' with the username '"
						+ dbUser_ExecutionLogStart_1_tDBConnection_1 + "'.");

				conn_ExecutionLogStart_1_tDBConnection_1 = java.sql.DriverManager.getConnection(
						url_ExecutionLogStart_1_tDBConnection_1, dbUser_ExecutionLogStart_1_tDBConnection_1,
						dbPwd_ExecutionLogStart_1_tDBConnection_1);
				log.debug("ExecutionLogStart_1_tDBConnection_1 - Connection to '"
						+ url_ExecutionLogStart_1_tDBConnection_1 + "' has succeeded.");

				globalMap.put("conn_ExecutionLogStart_1_tDBConnection_1", conn_ExecutionLogStart_1_tDBConnection_1);
				if (null != conn_ExecutionLogStart_1_tDBConnection_1) {

					log.debug("ExecutionLogStart_1_tDBConnection_1 - Connection is set auto commit to 'false'.");
					conn_ExecutionLogStart_1_tDBConnection_1.setAutoCommit(false);
				}

				globalMap.put("db_ExecutionLogStart_1_tDBConnection_1", "real_time");

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 main ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				tos_count_ExecutionLogStart_1_tDBConnection_1++;

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 main ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 process_data_begin ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 process_data_begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 process_data_end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 process_data_end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBConnection_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tDBConnection_1", true);
				end_Hash.put("ExecutionLogStart_1_tDBConnection_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:ExecutionLogStart_1_tDBConnection_1:OnSubjobOk",
						"", Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("ExecutionLogStart_1_OnSubjobOk1", 0, "ok");
			}

			ExecutionLogStart_1_tDBInput_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBConnection_1";

				/**
				 * [ExecutionLogStart_1_tDBConnection_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("ExecutionLogStart_1_tDBConnection_1_SUBPROCESS_STATE", 1);
	}

	public static class ExecutionLogStart_1_out1Struct
			implements routines.system.IPersistableRow<ExecutionLogStart_1_out1Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public int var1;

		public int getVar1() {
			return this.var1;
		}

		public Boolean var1IsNullable() {
			return false;
		}

		public Boolean var1IsKey() {
			return false;
		}

		public Integer var1Length() {
			return null;
		}

		public Integer var1Precision() {
			return null;
		}

		public String var1Default() {

			return null;

		}

		public String var1Comment() {

			return "";

		}

		public String var1Pattern() {

			return "";

		}

		public String var1OriginalDbColumnName() {

			return "var1";

		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.var1 = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.var1 = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.var1);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.var1);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("var1=" + String.valueOf(var1));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(var1);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(ExecutionLogStart_1_out1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class ExecutionLogStart_1_row3Struct
			implements routines.system.IPersistableRow<ExecutionLogStart_1_row3Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public int ExeID;

		public int getExeID() {
			return this.ExeID;
		}

		public Boolean ExeIDIsNullable() {
			return false;
		}

		public Boolean ExeIDIsKey() {
			return false;
		}

		public Integer ExeIDLength() {
			return 19;
		}

		public Integer ExeIDPrecision() {
			return 0;
		}

		public String ExeIDDefault() {

			return "";

		}

		public String ExeIDComment() {

			return "";

		}

		public String ExeIDPattern() {

			return "";

		}

		public String ExeIDOriginalDbColumnName() {

			return "ExeID";

		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.ExeID = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.ExeID = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.ExeID);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.ExeID);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("ExeID=" + String.valueOf(ExeID));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(ExeID);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(ExecutionLogStart_1_row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void ExecutionLogStart_1_tDBInput_1Process(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("ExecutionLogStart_1_tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "ExecutionLogStart_1_tDBInput_1");
		org.slf4j.MDC.put("_subJobPid", "HIh15b_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				ExecutionLogStart_1_row3Struct ExecutionLogStart_1_row3 = new ExecutionLogStart_1_row3Struct();
				ExecutionLogStart_1_out1Struct ExecutionLogStart_1_out1 = new ExecutionLogStart_1_out1Struct();

				/**
				 * [ExecutionLogStart_1_tLogRow_2 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tLogRow_2", false);
				start_Hash.put("ExecutionLogStart_1_tLogRow_2", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tLogRow_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0,
						"ExecutionLogStart_1_out1");

				int tos_count_ExecutionLogStart_1_tLogRow_2 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tLogRow_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tLogRow_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tLogRow_2 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("BASIC_MODE" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("TABLE_PRINT" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("VERTICAL" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("FIELDSEPARATOR" + " = " + "\"|\"");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("PRINT_HEADER" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("PRINT_UNIQUE_NAME" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("PRINT_COLNAMES" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append("USE_FIXED_LENGTH" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_2
									.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tLogRow_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tLogRow_2 - "
										+ (log4jParamters_ExecutionLogStart_1_tLogRow_2));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tLogRow_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tLogRow_2", "tLogRow_2", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				final String OUTPUT_FIELD_SEPARATOR_ExecutionLogStart_1_tLogRow_2 = "|";
				java.io.PrintStream consoleOut_ExecutionLogStart_1_tLogRow_2 = null;

				StringBuilder strBuffer_ExecutionLogStart_1_tLogRow_2 = null;
				int nb_line_ExecutionLogStart_1_tLogRow_2 = 0;
///////////////////////    			

				/**
				 * [ExecutionLogStart_1_tLogRow_2 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_2 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tMap_2", false);
				start_Hash.put("ExecutionLogStart_1_tMap_2", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tMap_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0,
						"ExecutionLogStart_1_row3");

				int tos_count_ExecutionLogStart_1_tMap_2 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tMap_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tMap_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tMap_2 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tMap_2.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tMap_2.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_ExecutionLogStart_1_tMap_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_2.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_ExecutionLogStart_1_tMap_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_2.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_ExecutionLogStart_1_tMap_2.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_2
									.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tMap_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug(
										"ExecutionLogStart_1_tMap_2 - " + (log4jParamters_ExecutionLogStart_1_tMap_2));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tMap_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tMap_2", "tMap_2", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_ExecutionLogStart_1_row3_ExecutionLogStart_1_tMap_2 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__ExecutionLogStart_1_tMap_2__Struct {
					int var1;
				}
				Var__ExecutionLogStart_1_tMap_2__Struct Var__ExecutionLogStart_1_tMap_2 = new Var__ExecutionLogStart_1_tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_ExecutionLogStart_1_out1_ExecutionLogStart_1_tMap_2 = 0;

				ExecutionLogStart_1_out1Struct ExecutionLogStart_1_out1_tmp = new ExecutionLogStart_1_out1Struct();
// ###############################

				/**
				 * [ExecutionLogStart_1_tMap_2 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBInput_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tDBInput_1", false);
				start_Hash.put("ExecutionLogStart_1_tDBInput_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tDBInput_1";

				int tos_count_ExecutionLogStart_1_tDBInput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBInput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tDBInput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tDBInput_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tDBInput_1
									.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1
									.append("TABLE" + " = " + "\"Execution_Log_Table\"");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("QUERY" + " = "
									+ "\"select coalesce(max(execution_ID),0) as ExeID from Execution_Log_Table\"");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("ExeID") + "}]");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBInput_1
									.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
							log4jParamters_ExecutionLogStart_1_tDBInput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tDBInput_1 - "
										+ (log4jParamters_ExecutionLogStart_1_tDBInput_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tDBInput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tDBInput_1", "tDBInput_1", "tMysqlInput");
					talendJobLogProcess(globalMap);
				}

				java.util.Calendar calendar_ExecutionLogStart_1_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_ExecutionLogStart_1_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_ExecutionLogStart_1_tDBInput_1 = calendar_ExecutionLogStart_1_tDBInput_1.getTime();
				int nb_line_ExecutionLogStart_1_tDBInput_1 = 0;
				java.sql.Connection conn_ExecutionLogStart_1_tDBInput_1 = null;
				conn_ExecutionLogStart_1_tDBInput_1 = (java.sql.Connection) globalMap
						.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_ExecutionLogStart_1_tDBInput_1 != null) {
					if (conn_ExecutionLogStart_1_tDBInput_1.getMetaData() != null) {

						log.debug("ExecutionLogStart_1_tDBInput_1 - Uses an existing connection with username '"
								+ conn_ExecutionLogStart_1_tDBInput_1.getMetaData().getUserName()
								+ "'. Connection URL: " + conn_ExecutionLogStart_1_tDBInput_1.getMetaData().getURL()
								+ ".");

					}
				}

				java.sql.Statement stmt_ExecutionLogStart_1_tDBInput_1 = conn_ExecutionLogStart_1_tDBInput_1
						.createStatement();

				String dbquery_ExecutionLogStart_1_tDBInput_1 = "select coalesce(max(execution_ID),0) as ExeID from Execution_Log_Table";

				log.debug("ExecutionLogStart_1_tDBInput_1 - Executing the query: '"
						+ dbquery_ExecutionLogStart_1_tDBInput_1 + "'.");

				globalMap.put("ExecutionLogStart_1_tDBInput_1_QUERY", dbquery_ExecutionLogStart_1_tDBInput_1);

				java.sql.ResultSet rs_ExecutionLogStart_1_tDBInput_1 = null;

				try {
					rs_ExecutionLogStart_1_tDBInput_1 = stmt_ExecutionLogStart_1_tDBInput_1
							.executeQuery(dbquery_ExecutionLogStart_1_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_ExecutionLogStart_1_tDBInput_1 = rs_ExecutionLogStart_1_tDBInput_1
							.getMetaData();
					int colQtyInRs_ExecutionLogStart_1_tDBInput_1 = rsmd_ExecutionLogStart_1_tDBInput_1
							.getColumnCount();

					String tmpContent_ExecutionLogStart_1_tDBInput_1 = null;

					log.debug("ExecutionLogStart_1_tDBInput_1 - Retrieving records from the database.");

					while (rs_ExecutionLogStart_1_tDBInput_1.next()) {
						nb_line_ExecutionLogStart_1_tDBInput_1++;

						if (colQtyInRs_ExecutionLogStart_1_tDBInput_1 < 1) {
							ExecutionLogStart_1_row3.ExeID = 0;
						} else {

							ExecutionLogStart_1_row3.ExeID = rs_ExecutionLogStart_1_tDBInput_1.getInt(1);
							if (rs_ExecutionLogStart_1_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}

						log.debug("ExecutionLogStart_1_tDBInput_1 - Retrieving the record "
								+ nb_line_ExecutionLogStart_1_tDBInput_1 + ".");

						/**
						 * [ExecutionLogStart_1_tDBInput_1 begin ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBInput_1 main ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBInput_1";

						tos_count_ExecutionLogStart_1_tDBInput_1++;

						/**
						 * [ExecutionLogStart_1_tDBInput_1 main ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBInput_1";

						/**
						 * [ExecutionLogStart_1_tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tMap_2 main ] start
						 */

						currentComponent = "ExecutionLogStart_1_tMap_2";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "ExecutionLogStart_1_row3", "ExecutionLogStart_1_tDBInput_1", "tDBInput_1",
								"tMysqlInput", "ExecutionLogStart_1_tMap_2", "tMap_2", "tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("ExecutionLogStart_1_row3 - "
									+ (ExecutionLogStart_1_row3 == null ? "" : ExecutionLogStart_1_row3.toLogString()));
						}

						boolean hasCasePrimitiveKeyWithNull_ExecutionLogStart_1_tMap_2 = false;

						// ###############################
						// # Input tables (lookups)

						boolean rejectedInnerJoin_ExecutionLogStart_1_tMap_2 = false;
						boolean mainRowRejected_ExecutionLogStart_1_tMap_2 = false;
						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__ExecutionLogStart_1_tMap_2__Struct Var = Var__ExecutionLogStart_1_tMap_2;
							Var.var1 = context.exeID = ExecutionLogStart_1_row3.ExeID + 1;// ###############################
							// ###############################
							// # Output tables

							ExecutionLogStart_1_out1 = null;

// # Output table : 'ExecutionLogStart_1_out1'
							count_ExecutionLogStart_1_out1_ExecutionLogStart_1_tMap_2++;

							ExecutionLogStart_1_out1_tmp.var1 = context.exeID;
							ExecutionLogStart_1_out1 = ExecutionLogStart_1_out1_tmp;
							log.debug("ExecutionLogStart_1_tMap_2 - Outputting the record "
									+ count_ExecutionLogStart_1_out1_ExecutionLogStart_1_tMap_2
									+ " of the output table 'ExecutionLogStart_1_out1'.");

// ###############################

						} // end of Var scope

						rejectedInnerJoin_ExecutionLogStart_1_tMap_2 = false;

						tos_count_ExecutionLogStart_1_tMap_2++;

						/**
						 * [ExecutionLogStart_1_tMap_2 main ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tMap_2 process_data_begin ] start
						 */

						currentComponent = "ExecutionLogStart_1_tMap_2";

						/**
						 * [ExecutionLogStart_1_tMap_2 process_data_begin ] stop
						 */
// Start of branch "ExecutionLogStart_1_out1"
						if (ExecutionLogStart_1_out1 != null) {

							/**
							 * [ExecutionLogStart_1_tLogRow_2 main ] start
							 */

							currentComponent = "ExecutionLogStart_1_tLogRow_2";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "ExecutionLogStart_1_out1", "ExecutionLogStart_1_tMap_2", "tMap_2", "tMap",
									"ExecutionLogStart_1_tLogRow_2", "tLogRow_2", "tLogRow"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("ExecutionLogStart_1_out1 - " + (ExecutionLogStart_1_out1 == null ? ""
										: ExecutionLogStart_1_out1.toLogString()));
							}

///////////////////////		

							strBuffer_ExecutionLogStart_1_tLogRow_2 = new StringBuilder();

							strBuffer_ExecutionLogStart_1_tLogRow_2
									.append(String.valueOf(ExecutionLogStart_1_out1.var1));

							if (globalMap.get("tLogRow_CONSOLE") != null) {
								consoleOut_ExecutionLogStart_1_tLogRow_2 = (java.io.PrintStream) globalMap
										.get("tLogRow_CONSOLE");
							} else {
								consoleOut_ExecutionLogStart_1_tLogRow_2 = new java.io.PrintStream(
										new java.io.BufferedOutputStream(System.out));
								globalMap.put("tLogRow_CONSOLE", consoleOut_ExecutionLogStart_1_tLogRow_2);
							}
							log.info("ExecutionLogStart_1_tLogRow_2 - Content of row "
									+ (nb_line_ExecutionLogStart_1_tLogRow_2 + 1) + ": "
									+ strBuffer_ExecutionLogStart_1_tLogRow_2.toString());
							consoleOut_ExecutionLogStart_1_tLogRow_2
									.println(strBuffer_ExecutionLogStart_1_tLogRow_2.toString());
							consoleOut_ExecutionLogStart_1_tLogRow_2.flush();
							nb_line_ExecutionLogStart_1_tLogRow_2++;
//////

//////                    

///////////////////////    			

							tos_count_ExecutionLogStart_1_tLogRow_2++;

							/**
							 * [ExecutionLogStart_1_tLogRow_2 main ] stop
							 */

							/**
							 * [ExecutionLogStart_1_tLogRow_2 process_data_begin ] start
							 */

							currentComponent = "ExecutionLogStart_1_tLogRow_2";

							/**
							 * [ExecutionLogStart_1_tLogRow_2 process_data_begin ] stop
							 */

							/**
							 * [ExecutionLogStart_1_tLogRow_2 process_data_end ] start
							 */

							currentComponent = "ExecutionLogStart_1_tLogRow_2";

							/**
							 * [ExecutionLogStart_1_tLogRow_2 process_data_end ] stop
							 */

						} // End of branch "ExecutionLogStart_1_out1"

						/**
						 * [ExecutionLogStart_1_tMap_2 process_data_end ] start
						 */

						currentComponent = "ExecutionLogStart_1_tMap_2";

						/**
						 * [ExecutionLogStart_1_tMap_2 process_data_end ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBInput_1 process_data_end ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBInput_1";

						/**
						 * [ExecutionLogStart_1_tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBInput_1 end ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBInput_1";

					}
				} finally {
					if (rs_ExecutionLogStart_1_tDBInput_1 != null) {
						rs_ExecutionLogStart_1_tDBInput_1.close();
					}
					if (stmt_ExecutionLogStart_1_tDBInput_1 != null) {
						stmt_ExecutionLogStart_1_tDBInput_1.close();
					}
				}
				globalMap.put("ExecutionLogStart_1_tDBInput_1_NB_LINE", nb_line_ExecutionLogStart_1_tDBInput_1);
				log.debug("ExecutionLogStart_1_tDBInput_1 - Retrieved records count: "
						+ nb_line_ExecutionLogStart_1_tDBInput_1 + " .");

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBInput_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tDBInput_1", true);
				end_Hash.put("ExecutionLogStart_1_tDBInput_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tDBInput_1 end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_2 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tMap_2";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("ExecutionLogStart_1_tMap_2 - Written records count in the table 'ExecutionLogStart_1_out1': "
						+ count_ExecutionLogStart_1_out1_ExecutionLogStart_1_tMap_2 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId,
						"ExecutionLogStart_1_row3", 2, 0, "ExecutionLogStart_1_tDBInput_1", "tDBInput_1", "tMysqlInput",
						"ExecutionLogStart_1_tMap_2", "tMap_2", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tMap_2 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tMap_2", true);
				end_Hash.put("ExecutionLogStart_1_tMap_2", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tMap_2 end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tLogRow_2 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tLogRow_2";

//////
//////
				globalMap.put("ExecutionLogStart_1_tLogRow_2_NB_LINE", nb_line_ExecutionLogStart_1_tLogRow_2);
				if (log.isInfoEnabled())
					log.info("ExecutionLogStart_1_tLogRow_2 - " + ("Printed row count: ")
							+ (nb_line_ExecutionLogStart_1_tLogRow_2) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId,
						"ExecutionLogStart_1_out1", 2, 0, "ExecutionLogStart_1_tMap_2", "tMap_2", "tMap",
						"ExecutionLogStart_1_tLogRow_2", "tLogRow_2", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tLogRow_2 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tLogRow_2", true);
				end_Hash.put("ExecutionLogStart_1_tLogRow_2", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tLogRow_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:ExecutionLogStart_1_tDBInput_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("ExecutionLogStart_1_OnSubjobOk2", 0, "ok");
			}

			ExecutionLogStart_1_tRowGenerator_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [ExecutionLogStart_1_tDBInput_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBInput_1";

				/**
				 * [ExecutionLogStart_1_tDBInput_1 finally ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_2 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tMap_2";

				/**
				 * [ExecutionLogStart_1_tMap_2 finally ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tLogRow_2 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tLogRow_2";

				/**
				 * [ExecutionLogStart_1_tLogRow_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("ExecutionLogStart_1_tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class ExecutionLogStart_1_row2Struct
			implements routines.system.IPersistableRow<ExecutionLogStart_1_row2Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public Integer execution_ID;

		public Integer getExecution_ID() {
			return this.execution_ID;
		}

		public Boolean execution_IDIsNullable() {
			return true;
		}

		public Boolean execution_IDIsKey() {
			return false;
		}

		public Integer execution_IDLength() {
			return null;
		}

		public Integer execution_IDPrecision() {
			return null;
		}

		public String execution_IDDefault() {

			return null;

		}

		public String execution_IDComment() {

			return "";

		}

		public String execution_IDPattern() {

			return "";

		}

		public String execution_IDOriginalDbColumnName() {

			return "execution_ID";

		}

		public String process_ID;

		public String getProcess_ID() {
			return this.process_ID;
		}

		public Boolean process_IDIsNullable() {
			return true;
		}

		public Boolean process_IDIsKey() {
			return false;
		}

		public Integer process_IDLength() {
			return null;
		}

		public Integer process_IDPrecision() {
			return null;
		}

		public String process_IDDefault() {

			return null;

		}

		public String process_IDComment() {

			return "";

		}

		public String process_IDPattern() {

			return "";

		}

		public String process_IDOriginalDbColumnName() {

			return "process_ID";

		}

		public String Job_name;

		public String getJob_name() {
			return this.Job_name;
		}

		public Boolean Job_nameIsNullable() {
			return true;
		}

		public Boolean Job_nameIsKey() {
			return false;
		}

		public Integer Job_nameLength() {
			return null;
		}

		public Integer Job_namePrecision() {
			return null;
		}

		public String Job_nameDefault() {

			return null;

		}

		public String Job_nameComment() {

			return "";

		}

		public String Job_namePattern() {

			return "";

		}

		public String Job_nameOriginalDbColumnName() {

			return "Job_name";

		}

		public String project_name;

		public String getProject_name() {
			return this.project_name;
		}

		public Boolean project_nameIsNullable() {
			return true;
		}

		public Boolean project_nameIsKey() {
			return false;
		}

		public Integer project_nameLength() {
			return null;
		}

		public Integer project_namePrecision() {
			return null;
		}

		public String project_nameDefault() {

			return null;

		}

		public String project_nameComment() {

			return "";

		}

		public String project_namePattern() {

			return "";

		}

		public String project_nameOriginalDbColumnName() {

			return "project_name";

		}

		public String Job_start_status;

		public String getJob_start_status() {
			return this.Job_start_status;
		}

		public Boolean Job_start_statusIsNullable() {
			return true;
		}

		public Boolean Job_start_statusIsKey() {
			return false;
		}

		public Integer Job_start_statusLength() {
			return null;
		}

		public Integer Job_start_statusPrecision() {
			return null;
		}

		public String Job_start_statusDefault() {

			return null;

		}

		public String Job_start_statusComment() {

			return "";

		}

		public String Job_start_statusPattern() {

			return "";

		}

		public String Job_start_statusOriginalDbColumnName() {

			return "Job_start_status";

		}

		public java.util.Date job_start_time;

		public java.util.Date getJob_start_time() {
			return this.job_start_time;
		}

		public Boolean job_start_timeIsNullable() {
			return true;
		}

		public Boolean job_start_timeIsKey() {
			return false;
		}

		public Integer job_start_timeLength() {
			return null;
		}

		public Integer job_start_timePrecision() {
			return null;
		}

		public String job_start_timeDefault() {

			return null;

		}

		public String job_start_timeComment() {

			return "";

		}

		public String job_start_timePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String job_start_timeOriginalDbColumnName() {

			return "job_start_time";

		}

		public String error_message;

		public String getError_message() {
			return this.error_message;
		}

		public Boolean error_messageIsNullable() {
			return true;
		}

		public Boolean error_messageIsKey() {
			return false;
		}

		public Integer error_messageLength() {
			return null;
		}

		public Integer error_messagePrecision() {
			return null;
		}

		public String error_messageDefault() {

			return null;

		}

		public String error_messageComment() {

			return "";

		}

		public String error_messagePattern() {

			return "";

		}

		public String error_messageOriginalDbColumnName() {

			return "error_message";

		}

		public String job_end_status;

		public String getJob_end_status() {
			return this.job_end_status;
		}

		public Boolean job_end_statusIsNullable() {
			return true;
		}

		public Boolean job_end_statusIsKey() {
			return false;
		}

		public Integer job_end_statusLength() {
			return null;
		}

		public Integer job_end_statusPrecision() {
			return null;
		}

		public String job_end_statusDefault() {

			return null;

		}

		public String job_end_statusComment() {

			return "";

		}

		public String job_end_statusPattern() {

			return "";

		}

		public String job_end_statusOriginalDbColumnName() {

			return "job_end_status";

		}

		public java.util.Date job_end_time;

		public java.util.Date getJob_end_time() {
			return this.job_end_time;
		}

		public Boolean job_end_timeIsNullable() {
			return true;
		}

		public Boolean job_end_timeIsKey() {
			return false;
		}

		public Integer job_end_timeLength() {
			return null;
		}

		public Integer job_end_timePrecision() {
			return null;
		}

		public String job_end_timeDefault() {

			return null;

		}

		public String job_end_timeComment() {

			return "";

		}

		public String job_end_timePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String job_end_timeOriginalDbColumnName() {

			return "job_end_time";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.Job_name = readString(dis);

					this.project_name = readString(dis);

					this.Job_start_status = readString(dis);

					this.job_start_time = readDate(dis);

					this.error_message = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.Job_name = readString(dis);

					this.project_name = readString(dis);

					this.Job_start_status = readString(dis);

					this.job_start_time = readDate(dis);

					this.error_message = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.Job_name, dos);

				// String

				writeString(this.project_name, dos);

				// String

				writeString(this.Job_start_status, dos);

				// java.util.Date

				writeDate(this.job_start_time, dos);

				// String

				writeString(this.error_message, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.Job_name, dos);

				// String

				writeString(this.project_name, dos);

				// String

				writeString(this.Job_start_status, dos);

				// java.util.Date

				writeDate(this.job_start_time, dos);

				// String

				writeString(this.error_message, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("execution_ID=" + String.valueOf(execution_ID));
			sb.append(",process_ID=" + process_ID);
			sb.append(",Job_name=" + Job_name);
			sb.append(",project_name=" + project_name);
			sb.append(",Job_start_status=" + Job_start_status);
			sb.append(",job_start_time=" + String.valueOf(job_start_time));
			sb.append(",error_message=" + error_message);
			sb.append(",job_end_status=" + job_end_status);
			sb.append(",job_end_time=" + String.valueOf(job_end_time));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (execution_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(execution_ID);
			}

			sb.append("|");

			if (process_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(process_ID);
			}

			sb.append("|");

			if (Job_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Job_name);
			}

			sb.append("|");

			if (project_name == null) {
				sb.append("<null>");
			} else {
				sb.append(project_name);
			}

			sb.append("|");

			if (Job_start_status == null) {
				sb.append("<null>");
			} else {
				sb.append(Job_start_status);
			}

			sb.append("|");

			if (job_start_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_start_time);
			}

			sb.append("|");

			if (error_message == null) {
				sb.append("<null>");
			} else {
				sb.append(error_message);
			}

			sb.append("|");

			if (job_end_status == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_status);
			}

			sb.append("|");

			if (job_end_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_time);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(ExecutionLogStart_1_row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class ExecutionLogStart_1_Execution_log_ColumnsStruct
			implements routines.system.IPersistableRow<ExecutionLogStart_1_Execution_log_ColumnsStruct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public Integer execution_ID;

		public Integer getExecution_ID() {
			return this.execution_ID;
		}

		public Boolean execution_IDIsNullable() {
			return true;
		}

		public Boolean execution_IDIsKey() {
			return false;
		}

		public Integer execution_IDLength() {
			return null;
		}

		public Integer execution_IDPrecision() {
			return null;
		}

		public String execution_IDDefault() {

			return null;

		}

		public String execution_IDComment() {

			return "";

		}

		public String execution_IDPattern() {

			return "";

		}

		public String execution_IDOriginalDbColumnName() {

			return "execution_ID";

		}

		public String process_ID;

		public String getProcess_ID() {
			return this.process_ID;
		}

		public Boolean process_IDIsNullable() {
			return true;
		}

		public Boolean process_IDIsKey() {
			return false;
		}

		public Integer process_IDLength() {
			return null;
		}

		public Integer process_IDPrecision() {
			return null;
		}

		public String process_IDDefault() {

			return null;

		}

		public String process_IDComment() {

			return "";

		}

		public String process_IDPattern() {

			return "";

		}

		public String process_IDOriginalDbColumnName() {

			return "process_ID";

		}

		public String Job_name;

		public String getJob_name() {
			return this.Job_name;
		}

		public Boolean Job_nameIsNullable() {
			return true;
		}

		public Boolean Job_nameIsKey() {
			return false;
		}

		public Integer Job_nameLength() {
			return null;
		}

		public Integer Job_namePrecision() {
			return null;
		}

		public String Job_nameDefault() {

			return null;

		}

		public String Job_nameComment() {

			return "";

		}

		public String Job_namePattern() {

			return "";

		}

		public String Job_nameOriginalDbColumnName() {

			return "Job_name";

		}

		public String project_name;

		public String getProject_name() {
			return this.project_name;
		}

		public Boolean project_nameIsNullable() {
			return true;
		}

		public Boolean project_nameIsKey() {
			return false;
		}

		public Integer project_nameLength() {
			return null;
		}

		public Integer project_namePrecision() {
			return null;
		}

		public String project_nameDefault() {

			return null;

		}

		public String project_nameComment() {

			return "";

		}

		public String project_namePattern() {

			return "";

		}

		public String project_nameOriginalDbColumnName() {

			return "project_name";

		}

		public String Job_start_status;

		public String getJob_start_status() {
			return this.Job_start_status;
		}

		public Boolean Job_start_statusIsNullable() {
			return true;
		}

		public Boolean Job_start_statusIsKey() {
			return false;
		}

		public Integer Job_start_statusLength() {
			return null;
		}

		public Integer Job_start_statusPrecision() {
			return null;
		}

		public String Job_start_statusDefault() {

			return null;

		}

		public String Job_start_statusComment() {

			return "";

		}

		public String Job_start_statusPattern() {

			return "";

		}

		public String Job_start_statusOriginalDbColumnName() {

			return "Job_start_status";

		}

		public java.util.Date job_start_time;

		public java.util.Date getJob_start_time() {
			return this.job_start_time;
		}

		public Boolean job_start_timeIsNullable() {
			return true;
		}

		public Boolean job_start_timeIsKey() {
			return false;
		}

		public Integer job_start_timeLength() {
			return null;
		}

		public Integer job_start_timePrecision() {
			return null;
		}

		public String job_start_timeDefault() {

			return null;

		}

		public String job_start_timeComment() {

			return "";

		}

		public String job_start_timePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String job_start_timeOriginalDbColumnName() {

			return "job_start_time";

		}

		public String error_message;

		public String getError_message() {
			return this.error_message;
		}

		public Boolean error_messageIsNullable() {
			return true;
		}

		public Boolean error_messageIsKey() {
			return false;
		}

		public Integer error_messageLength() {
			return null;
		}

		public Integer error_messagePrecision() {
			return null;
		}

		public String error_messageDefault() {

			return null;

		}

		public String error_messageComment() {

			return "";

		}

		public String error_messagePattern() {

			return "";

		}

		public String error_messageOriginalDbColumnName() {

			return "error_message";

		}

		public String job_end_status;

		public String getJob_end_status() {
			return this.job_end_status;
		}

		public Boolean job_end_statusIsNullable() {
			return true;
		}

		public Boolean job_end_statusIsKey() {
			return false;
		}

		public Integer job_end_statusLength() {
			return null;
		}

		public Integer job_end_statusPrecision() {
			return null;
		}

		public String job_end_statusDefault() {

			return null;

		}

		public String job_end_statusComment() {

			return "";

		}

		public String job_end_statusPattern() {

			return "";

		}

		public String job_end_statusOriginalDbColumnName() {

			return "job_end_status";

		}

		public java.util.Date job_end_time;

		public java.util.Date getJob_end_time() {
			return this.job_end_time;
		}

		public Boolean job_end_timeIsNullable() {
			return true;
		}

		public Boolean job_end_timeIsKey() {
			return false;
		}

		public Integer job_end_timeLength() {
			return null;
		}

		public Integer job_end_timePrecision() {
			return null;
		}

		public String job_end_timeDefault() {

			return null;

		}

		public String job_end_timeComment() {

			return "";

		}

		public String job_end_timePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String job_end_timeOriginalDbColumnName() {

			return "job_end_time";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.Job_name = readString(dis);

					this.project_name = readString(dis);

					this.Job_start_status = readString(dis);

					this.job_start_time = readDate(dis);

					this.error_message = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.Job_name = readString(dis);

					this.project_name = readString(dis);

					this.Job_start_status = readString(dis);

					this.job_start_time = readDate(dis);

					this.error_message = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.Job_name, dos);

				// String

				writeString(this.project_name, dos);

				// String

				writeString(this.Job_start_status, dos);

				// java.util.Date

				writeDate(this.job_start_time, dos);

				// String

				writeString(this.error_message, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.Job_name, dos);

				// String

				writeString(this.project_name, dos);

				// String

				writeString(this.Job_start_status, dos);

				// java.util.Date

				writeDate(this.job_start_time, dos);

				// String

				writeString(this.error_message, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("execution_ID=" + String.valueOf(execution_ID));
			sb.append(",process_ID=" + process_ID);
			sb.append(",Job_name=" + Job_name);
			sb.append(",project_name=" + project_name);
			sb.append(",Job_start_status=" + Job_start_status);
			sb.append(",job_start_time=" + String.valueOf(job_start_time));
			sb.append(",error_message=" + error_message);
			sb.append(",job_end_status=" + job_end_status);
			sb.append(",job_end_time=" + String.valueOf(job_end_time));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (execution_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(execution_ID);
			}

			sb.append("|");

			if (process_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(process_ID);
			}

			sb.append("|");

			if (Job_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Job_name);
			}

			sb.append("|");

			if (project_name == null) {
				sb.append("<null>");
			} else {
				sb.append(project_name);
			}

			sb.append("|");

			if (Job_start_status == null) {
				sb.append("<null>");
			} else {
				sb.append(Job_start_status);
			}

			sb.append("|");

			if (job_start_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_start_time);
			}

			sb.append("|");

			if (error_message == null) {
				sb.append("<null>");
			} else {
				sb.append(error_message);
			}

			sb.append("|");

			if (job_end_status == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_status);
			}

			sb.append("|");

			if (job_end_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_time);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(ExecutionLogStart_1_Execution_log_ColumnsStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class ExecutionLogStart_1_row1Struct
			implements routines.system.IPersistableRow<ExecutionLogStart_1_row1Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String newColumn;

		public String getNewColumn() {
			return this.newColumn;
		}

		public Boolean newColumnIsNullable() {
			return true;
		}

		public Boolean newColumnIsKey() {
			return false;
		}

		public Integer newColumnLength() {
			return null;
		}

		public Integer newColumnPrecision() {
			return null;
		}

		public String newColumnDefault() {

			return "";

		}

		public String newColumnComment() {

			return "";

		}

		public String newColumnPattern() {

			return "";

		}

		public String newColumnOriginalDbColumnName() {

			return "newColumn";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.newColumn, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.newColumn, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("newColumn=" + newColumn);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (newColumn == null) {
				sb.append("<null>");
			} else {
				sb.append(newColumn);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(ExecutionLogStart_1_row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void ExecutionLogStart_1_tRowGenerator_1Process(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("ExecutionLogStart_1_tRowGenerator_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "ExecutionLogStart_1_tRowGenerator_1");
		org.slf4j.MDC.put("_subJobPid", "htIPev_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				ExecutionLogStart_1_row1Struct ExecutionLogStart_1_row1 = new ExecutionLogStart_1_row1Struct();
				ExecutionLogStart_1_Execution_log_ColumnsStruct ExecutionLogStart_1_Execution_log_Columns = new ExecutionLogStart_1_Execution_log_ColumnsStruct();
				ExecutionLogStart_1_Execution_log_ColumnsStruct ExecutionLogStart_1_row2 = ExecutionLogStart_1_Execution_log_Columns;

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tDBOutput_1", false);
				start_Hash.put("ExecutionLogStart_1_tDBOutput_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tDBOutput_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0,
						"ExecutionLogStart_1_row2");

				int tos_count_ExecutionLogStart_1_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tDBOutput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tDBOutput_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("TABLE" + " = " + "\"Execution_Log_Table\"");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("ADD_COLS" + " = " + "[]");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1
									.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_ExecutionLogStart_1_tDBOutput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tDBOutput_1 - "
										+ (log4jParamters_ExecutionLogStart_1_tDBOutput_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tDBOutput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tDBOutput_1", "tDBOutput_1", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_ExecutionLogStart_1_tDBOutput_1 = 0;
				int nb_line_update_ExecutionLogStart_1_tDBOutput_1 = 0;
				int nb_line_inserted_ExecutionLogStart_1_tDBOutput_1 = 0;
				int nb_line_deleted_ExecutionLogStart_1_tDBOutput_1 = 0;
				int nb_line_rejected_ExecutionLogStart_1_tDBOutput_1 = 0;

				int deletedCount_ExecutionLogStart_1_tDBOutput_1 = 0;
				int updatedCount_ExecutionLogStart_1_tDBOutput_1 = 0;
				int insertedCount_ExecutionLogStart_1_tDBOutput_1 = 0;
				int rowsToCommitCount_ExecutionLogStart_1_tDBOutput_1 = 0;
				int rejectedCount_ExecutionLogStart_1_tDBOutput_1 = 0;

				String tableName_ExecutionLogStart_1_tDBOutput_1 = "Execution_Log_Table";
				boolean whetherReject_ExecutionLogStart_1_tDBOutput_1 = false;

				java.util.Calendar calendar_ExecutionLogStart_1_tDBOutput_1 = java.util.Calendar.getInstance();
				calendar_ExecutionLogStart_1_tDBOutput_1.set(1, 0, 1, 0, 0, 0);
				long year1_ExecutionLogStart_1_tDBOutput_1 = calendar_ExecutionLogStart_1_tDBOutput_1.getTime()
						.getTime();
				calendar_ExecutionLogStart_1_tDBOutput_1.set(10000, 0, 1, 0, 0, 0);
				long year10000_ExecutionLogStart_1_tDBOutput_1 = calendar_ExecutionLogStart_1_tDBOutput_1.getTime()
						.getTime();
				long date_ExecutionLogStart_1_tDBOutput_1;

				java.sql.Connection conn_ExecutionLogStart_1_tDBOutput_1 = null;
				conn_ExecutionLogStart_1_tDBOutput_1 = (java.sql.Connection) globalMap
						.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Uses an existing connection with username '")
							+ (conn_ExecutionLogStart_1_tDBOutput_1.getMetaData().getUserName())
							+ ("'. Connection URL: ") + (conn_ExecutionLogStart_1_tDBOutput_1.getMetaData().getURL())
							+ ("."));

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Connection is set auto commit to '")
							+ (conn_ExecutionLogStart_1_tDBOutput_1.getAutoCommit()) + ("'."));

				int count_ExecutionLogStart_1_tDBOutput_1 = 0;

				String insert_ExecutionLogStart_1_tDBOutput_1 = "INSERT INTO `" + "Execution_Log_Table"
						+ "` (`execution_ID`,`process_ID`,`Job_name`,`project_name`,`Job_start_status`,`job_start_time`,`error_message`,`job_end_status`,`job_end_time`) VALUES (?,?,?,?,?,?,?,?,?)";

				int batchSize_ExecutionLogStart_1_tDBOutput_1 = 100;
				int batchSizeCounter_ExecutionLogStart_1_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_ExecutionLogStart_1_tDBOutput_1 = conn_ExecutionLogStart_1_tDBOutput_1
						.prepareStatement(insert_ExecutionLogStart_1_tDBOutput_1);
				resourceMap.put("pstmt_ExecutionLogStart_1_tDBOutput_1", pstmt_ExecutionLogStart_1_tDBOutput_1);

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tLogRow_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tLogRow_1", false);
				start_Hash.put("ExecutionLogStart_1_tLogRow_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tLogRow_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0,
						"ExecutionLogStart_1_Execution_log_Columns");

				int tos_count_ExecutionLogStart_1_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tLogRow_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tLogRow_1
									.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tLogRow_1 - "
										+ (log4jParamters_ExecutionLogStart_1_tLogRow_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_ExecutionLogStart_1_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[9];

					public void addRow(String[] row) {

						for (int i = 0; i < 9; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 8 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 8 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|%6$-");
							sbformat.append(colLengths[5]);
							sbformat.append("s");

							sbformat.append("|%7$-");
							sbformat.append(colLengths[6]);
							sbformat.append("s");

							sbformat.append("|%8$-");
							sbformat.append(colLengths[7]);
							sbformat.append("s");

							sbformat.append("|%9$-");
							sbformat.append(colLengths[8]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[4] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[5] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[6] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[7] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[8] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_ExecutionLogStart_1_tLogRow_1 util_ExecutionLogStart_1_tLogRow_1 = new Util_ExecutionLogStart_1_tLogRow_1();
				util_ExecutionLogStart_1_tLogRow_1.setTableName("ExecutionLogStart_1_tLogRow_1");
				util_ExecutionLogStart_1_tLogRow_1.addRow(
						new String[] { "execution_ID", "process_ID", "Job_name", "project_name", "Job_start_status",
								"job_start_time", "error_message", "job_end_status", "job_end_time", });
				StringBuilder strBuffer_ExecutionLogStart_1_tLogRow_1 = null;
				int nb_line_ExecutionLogStart_1_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [ExecutionLogStart_1_tLogRow_1 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tMap_1", false);
				start_Hash.put("ExecutionLogStart_1_tMap_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tMap_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0,
						"ExecutionLogStart_1_row1");

				int tos_count_ExecutionLogStart_1_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tMap_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tMap_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tMap_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tMap_1.append("Parameters:");
							log4jParamters_ExecutionLogStart_1_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_ExecutionLogStart_1_tMap_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_ExecutionLogStart_1_tMap_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_ExecutionLogStart_1_tMap_1.append(" | ");
							log4jParamters_ExecutionLogStart_1_tMap_1
									.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_ExecutionLogStart_1_tMap_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug(
										"ExecutionLogStart_1_tMap_1 - " + (log4jParamters_ExecutionLogStart_1_tMap_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tMap_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tMap_1", "tMap_1", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_ExecutionLogStart_1_row1_ExecutionLogStart_1_tMap_1 = 0;

// ###############################        

// ###############################
// # Vars initialization
// ###############################

// ###############################
// # Outputs initialization
				int count_ExecutionLogStart_1_Execution_log_Columns_ExecutionLogStart_1_tMap_1 = 0;

				ExecutionLogStart_1_Execution_log_ColumnsStruct ExecutionLogStart_1_Execution_log_Columns_tmp = new ExecutionLogStart_1_Execution_log_ColumnsStruct();
// ###############################

				/**
				 * [ExecutionLogStart_1_tMap_1 begin ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tRowGenerator_1 begin ] start
				 */

				ok_Hash.put("ExecutionLogStart_1_tRowGenerator_1", false);
				start_Hash.put("ExecutionLogStart_1_tRowGenerator_1", System.currentTimeMillis());

				currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

				int tos_count_ExecutionLogStart_1_tRowGenerator_1 = 0;

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tRowGenerator_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_ExecutionLogStart_1_tRowGenerator_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_ExecutionLogStart_1_tRowGenerator_1 = new StringBuilder();
							log4jParamters_ExecutionLogStart_1_tRowGenerator_1.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("ExecutionLogStart_1_tRowGenerator_1 - "
										+ (log4jParamters_ExecutionLogStart_1_tRowGenerator_1));
						}
					}
					new BytesLimit65535_ExecutionLogStart_1_tRowGenerator_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("ExecutionLogStart_1_tRowGenerator_1", "tRowGenerator_1", "tRowGenerator");
					talendJobLogProcess(globalMap);
				}

				int nb_line_ExecutionLogStart_1_tRowGenerator_1 = 0;
				int nb_max_row_ExecutionLogStart_1_tRowGenerator_1 = 1;

				class ExecutionLogStart_1_tRowGenerator_1Randomizer {
					public String getRandomnewColumn() {

						return Mathematical.FFLT(3.14);

					}
				}
				ExecutionLogStart_1_tRowGenerator_1Randomizer randExecutionLogStart_1_tRowGenerator_1 = new ExecutionLogStart_1_tRowGenerator_1Randomizer();

				log.info("ExecutionLogStart_1_tRowGenerator_1 - Generating records.");
				for (int iExecutionLogStart_1_tRowGenerator_1 = 0; iExecutionLogStart_1_tRowGenerator_1 < nb_max_row_ExecutionLogStart_1_tRowGenerator_1; iExecutionLogStart_1_tRowGenerator_1++) {
					ExecutionLogStart_1_row1.newColumn = randExecutionLogStart_1_tRowGenerator_1.getRandomnewColumn();
					nb_line_ExecutionLogStart_1_tRowGenerator_1++;

					log.debug("ExecutionLogStart_1_tRowGenerator_1 - Retrieving the record "
							+ nb_line_ExecutionLogStart_1_tRowGenerator_1 + ".");

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 begin ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 main ] start
					 */

					currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

					tos_count_ExecutionLogStart_1_tRowGenerator_1++;

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 main ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 process_data_begin ] start
					 */

					currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 process_data_begin ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tMap_1 main ] start
					 */

					currentComponent = "ExecutionLogStart_1_tMap_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "ExecutionLogStart_1_row1", "ExecutionLogStart_1_tRowGenerator_1", "tRowGenerator_1",
							"tRowGenerator", "ExecutionLogStart_1_tMap_1", "tMap_1", "tMap"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("ExecutionLogStart_1_row1 - "
								+ (ExecutionLogStart_1_row1 == null ? "" : ExecutionLogStart_1_row1.toLogString()));
					}

					boolean hasCasePrimitiveKeyWithNull_ExecutionLogStart_1_tMap_1 = false;

					// ###############################
					// # Input tables (lookups)

					boolean rejectedInnerJoin_ExecutionLogStart_1_tMap_1 = false;
					boolean mainRowRejected_ExecutionLogStart_1_tMap_1 = false;
					// ###############################
					{ // start of Var scope

						// ###############################
						// # Vars tables
						// ###############################
						// ###############################
						// # Output tables

						ExecutionLogStart_1_Execution_log_Columns = null;

// # Output table : 'ExecutionLogStart_1_Execution_log_Columns'
						count_ExecutionLogStart_1_Execution_log_Columns_ExecutionLogStart_1_tMap_1++;

						ExecutionLogStart_1_Execution_log_Columns_tmp.execution_ID = context.exeID;
						ExecutionLogStart_1_Execution_log_Columns_tmp.process_ID = pid;
						ExecutionLogStart_1_Execution_log_Columns_tmp.Job_name = jobName;
						ExecutionLogStart_1_Execution_log_Columns_tmp.project_name = projectName;
						ExecutionLogStart_1_Execution_log_Columns_tmp.Job_start_status = "Start Success";
						ExecutionLogStart_1_Execution_log_Columns_tmp.job_start_time = TalendDate.getCurrentDate();
						ExecutionLogStart_1_Execution_log_Columns_tmp.error_message = null;
						ExecutionLogStart_1_Execution_log_Columns_tmp.job_end_status = null;
						ExecutionLogStart_1_Execution_log_Columns_tmp.job_end_time = null;
						ExecutionLogStart_1_Execution_log_Columns = ExecutionLogStart_1_Execution_log_Columns_tmp;
						log.debug("ExecutionLogStart_1_tMap_1 - Outputting the record "
								+ count_ExecutionLogStart_1_Execution_log_Columns_ExecutionLogStart_1_tMap_1
								+ " of the output table 'ExecutionLogStart_1_Execution_log_Columns'.");

// ###############################

					} // end of Var scope

					rejectedInnerJoin_ExecutionLogStart_1_tMap_1 = false;

					tos_count_ExecutionLogStart_1_tMap_1++;

					/**
					 * [ExecutionLogStart_1_tMap_1 main ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tMap_1 process_data_begin ] start
					 */

					currentComponent = "ExecutionLogStart_1_tMap_1";

					/**
					 * [ExecutionLogStart_1_tMap_1 process_data_begin ] stop
					 */
// Start of branch "ExecutionLogStart_1_Execution_log_Columns"
					if (ExecutionLogStart_1_Execution_log_Columns != null) {

						/**
						 * [ExecutionLogStart_1_tLogRow_1 main ] start
						 */

						currentComponent = "ExecutionLogStart_1_tLogRow_1";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "ExecutionLogStart_1_Execution_log_Columns", "ExecutionLogStart_1_tMap_1", "tMap_1",
								"tMap", "ExecutionLogStart_1_tLogRow_1", "tLogRow_1", "tLogRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("ExecutionLogStart_1_Execution_log_Columns - "
									+ (ExecutionLogStart_1_Execution_log_Columns == null ? ""
											: ExecutionLogStart_1_Execution_log_Columns.toLogString()));
						}

///////////////////////		

						String[] row_ExecutionLogStart_1_tLogRow_1 = new String[9];

						if (ExecutionLogStart_1_Execution_log_Columns.execution_ID != null) { //
							row_ExecutionLogStart_1_tLogRow_1[0] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.execution_ID);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.process_ID != null) { //
							row_ExecutionLogStart_1_tLogRow_1[1] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.process_ID);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.Job_name != null) { //
							row_ExecutionLogStart_1_tLogRow_1[2] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.Job_name);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.project_name != null) { //
							row_ExecutionLogStart_1_tLogRow_1[3] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.project_name);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.Job_start_status != null) { //
							row_ExecutionLogStart_1_tLogRow_1[4] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.Job_start_status);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.job_start_time != null) { //
							row_ExecutionLogStart_1_tLogRow_1[5] = FormatterUtils.format_Date(
									ExecutionLogStart_1_Execution_log_Columns.job_start_time, "yyyy-MM-dd HH:mm:ss");

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.error_message != null) { //
							row_ExecutionLogStart_1_tLogRow_1[6] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.error_message);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.job_end_status != null) { //
							row_ExecutionLogStart_1_tLogRow_1[7] = String
									.valueOf(ExecutionLogStart_1_Execution_log_Columns.job_end_status);

						} //

						if (ExecutionLogStart_1_Execution_log_Columns.job_end_time != null) { //
							row_ExecutionLogStart_1_tLogRow_1[8] = FormatterUtils.format_Date(
									ExecutionLogStart_1_Execution_log_Columns.job_end_time, "yyyy-MM-dd HH:mm:ss");

						} //

						util_ExecutionLogStart_1_tLogRow_1.addRow(row_ExecutionLogStart_1_tLogRow_1);
						nb_line_ExecutionLogStart_1_tLogRow_1++;
						log.info("ExecutionLogStart_1_tLogRow_1 - Content of row "
								+ nb_line_ExecutionLogStart_1_tLogRow_1 + ": "
								+ TalendString.unionString("|", row_ExecutionLogStart_1_tLogRow_1));
//////

//////                    

///////////////////////    			

						ExecutionLogStart_1_row2 = ExecutionLogStart_1_Execution_log_Columns;

						tos_count_ExecutionLogStart_1_tLogRow_1++;

						/**
						 * [ExecutionLogStart_1_tLogRow_1 main ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tLogRow_1 process_data_begin ] start
						 */

						currentComponent = "ExecutionLogStart_1_tLogRow_1";

						/**
						 * [ExecutionLogStart_1_tLogRow_1 process_data_begin ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 main ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBOutput_1";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "ExecutionLogStart_1_row2", "ExecutionLogStart_1_tLogRow_1", "tLogRow_1", "tLogRow",
								"ExecutionLogStart_1_tDBOutput_1", "tDBOutput_1", "tMysqlOutput"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("ExecutionLogStart_1_row2 - "
									+ (ExecutionLogStart_1_row2 == null ? "" : ExecutionLogStart_1_row2.toLogString()));
						}

						whetherReject_ExecutionLogStart_1_tDBOutput_1 = false;
						if (ExecutionLogStart_1_row2.execution_ID == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(1, java.sql.Types.INTEGER);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setInt(1, ExecutionLogStart_1_row2.execution_ID);
						}

						if (ExecutionLogStart_1_row2.process_ID == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(2, ExecutionLogStart_1_row2.process_ID);
						}

						if (ExecutionLogStart_1_row2.Job_name == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(3, ExecutionLogStart_1_row2.Job_name);
						}

						if (ExecutionLogStart_1_row2.project_name == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(4, ExecutionLogStart_1_row2.project_name);
						}

						if (ExecutionLogStart_1_row2.Job_start_status == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(5,
									ExecutionLogStart_1_row2.Job_start_status);
						}

						if (ExecutionLogStart_1_row2.job_start_time != null) {
							date_ExecutionLogStart_1_tDBOutput_1 = ExecutionLogStart_1_row2.job_start_time.getTime();
							if (date_ExecutionLogStart_1_tDBOutput_1 < year1_ExecutionLogStart_1_tDBOutput_1
									|| date_ExecutionLogStart_1_tDBOutput_1 >= year10000_ExecutionLogStart_1_tDBOutput_1) {
								pstmt_ExecutionLogStart_1_tDBOutput_1.setString(6, "0000-00-00 00:00:00");
							} else {
								pstmt_ExecutionLogStart_1_tDBOutput_1.setTimestamp(6,
										new java.sql.Timestamp(date_ExecutionLogStart_1_tDBOutput_1));
							}
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(6, java.sql.Types.DATE);
						}

						if (ExecutionLogStart_1_row2.error_message == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(7, ExecutionLogStart_1_row2.error_message);
						}

						if (ExecutionLogStart_1_row2.job_end_status == null) {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setString(8, ExecutionLogStart_1_row2.job_end_status);
						}

						if (ExecutionLogStart_1_row2.job_end_time != null) {
							date_ExecutionLogStart_1_tDBOutput_1 = ExecutionLogStart_1_row2.job_end_time.getTime();
							if (date_ExecutionLogStart_1_tDBOutput_1 < year1_ExecutionLogStart_1_tDBOutput_1
									|| date_ExecutionLogStart_1_tDBOutput_1 >= year10000_ExecutionLogStart_1_tDBOutput_1) {
								pstmt_ExecutionLogStart_1_tDBOutput_1.setString(9, "0000-00-00 00:00:00");
							} else {
								pstmt_ExecutionLogStart_1_tDBOutput_1.setTimestamp(9,
										new java.sql.Timestamp(date_ExecutionLogStart_1_tDBOutput_1));
							}
						} else {
							pstmt_ExecutionLogStart_1_tDBOutput_1.setNull(9, java.sql.Types.DATE);
						}

						pstmt_ExecutionLogStart_1_tDBOutput_1.addBatch();
						nb_line_ExecutionLogStart_1_tDBOutput_1++;

						if (log.isDebugEnabled())
							log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Adding the record ")
									+ (nb_line_ExecutionLogStart_1_tDBOutput_1) + (" to the ") + ("INSERT")
									+ (" batch."));
						batchSizeCounter_ExecutionLogStart_1_tDBOutput_1++;
						if (batchSize_ExecutionLogStart_1_tDBOutput_1 <= batchSizeCounter_ExecutionLogStart_1_tDBOutput_1) {
							try {
								int countSum_ExecutionLogStart_1_tDBOutput_1 = 0;
								if (log.isDebugEnabled())
									log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Executing the ") + ("INSERT")
											+ (" batch."));
								for (int countEach_ExecutionLogStart_1_tDBOutput_1 : pstmt_ExecutionLogStart_1_tDBOutput_1
										.executeBatch()) {
									countSum_ExecutionLogStart_1_tDBOutput_1 += (countEach_ExecutionLogStart_1_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED
											? 0
											: 1);
								}
								rowsToCommitCount_ExecutionLogStart_1_tDBOutput_1 += countSum_ExecutionLogStart_1_tDBOutput_1;
								if (log.isDebugEnabled())
									log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("The ") + ("INSERT")
											+ (" batch execution has succeeded."));
								insertedCount_ExecutionLogStart_1_tDBOutput_1 += countSum_ExecutionLogStart_1_tDBOutput_1;
							} catch (java.sql.BatchUpdateException e) {
								globalMap.put("ExecutionLogStart_1_tDBOutput_1_ERROR_MESSAGE", e.getMessage());
								throw (e);
							}

							batchSizeCounter_ExecutionLogStart_1_tDBOutput_1 = 0;
						}

						tos_count_ExecutionLogStart_1_tDBOutput_1++;

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 main ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 process_data_begin ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBOutput_1";

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 process_data_begin ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 process_data_end ] start
						 */

						currentComponent = "ExecutionLogStart_1_tDBOutput_1";

						/**
						 * [ExecutionLogStart_1_tDBOutput_1 process_data_end ] stop
						 */

						/**
						 * [ExecutionLogStart_1_tLogRow_1 process_data_end ] start
						 */

						currentComponent = "ExecutionLogStart_1_tLogRow_1";

						/**
						 * [ExecutionLogStart_1_tLogRow_1 process_data_end ] stop
						 */

					} // End of branch "ExecutionLogStart_1_Execution_log_Columns"

					/**
					 * [ExecutionLogStart_1_tMap_1 process_data_end ] start
					 */

					currentComponent = "ExecutionLogStart_1_tMap_1";

					/**
					 * [ExecutionLogStart_1_tMap_1 process_data_end ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 process_data_end ] start
					 */

					currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 process_data_end ] stop
					 */

					/**
					 * [ExecutionLogStart_1_tRowGenerator_1 end ] start
					 */

					currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

				}
				globalMap.put("ExecutionLogStart_1_tRowGenerator_1_NB_LINE",
						nb_line_ExecutionLogStart_1_tRowGenerator_1);
				log.info("ExecutionLogStart_1_tRowGenerator_1 - Generated records count:"
						+ nb_line_ExecutionLogStart_1_tRowGenerator_1 + " .");

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tRowGenerator_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tRowGenerator_1", true);
				end_Hash.put("ExecutionLogStart_1_tRowGenerator_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tRowGenerator_1 end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_1 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug(
						"ExecutionLogStart_1_tMap_1 - Written records count in the table 'ExecutionLogStart_1_Execution_log_Columns': "
								+ count_ExecutionLogStart_1_Execution_log_Columns_ExecutionLogStart_1_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId,
						"ExecutionLogStart_1_row1", 2, 0, "ExecutionLogStart_1_tRowGenerator_1", "tRowGenerator_1",
						"tRowGenerator", "ExecutionLogStart_1_tMap_1", "tMap_1", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tMap_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tMap_1", true);
				end_Hash.put("ExecutionLogStart_1_tMap_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tMap_1 end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tLogRow_1 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tLogRow_1";

//////

				java.io.PrintStream consoleOut_ExecutionLogStart_1_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_ExecutionLogStart_1_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_ExecutionLogStart_1_tLogRow_1 = new java.io.PrintStream(
							new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_ExecutionLogStart_1_tLogRow_1);
				}

				consoleOut_ExecutionLogStart_1_tLogRow_1
						.println(util_ExecutionLogStart_1_tLogRow_1.format().toString());
				consoleOut_ExecutionLogStart_1_tLogRow_1.flush();
//////
				globalMap.put("ExecutionLogStart_1_tLogRow_1_NB_LINE", nb_line_ExecutionLogStart_1_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("ExecutionLogStart_1_tLogRow_1 - " + ("Printed row count: ")
							+ (nb_line_ExecutionLogStart_1_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId,
						"ExecutionLogStart_1_Execution_log_Columns", 2, 0, "ExecutionLogStart_1_tMap_1", "tMap_1",
						"tMap", "ExecutionLogStart_1_tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tLogRow_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tLogRow_1", true);
				end_Hash.put("ExecutionLogStart_1_tLogRow_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tLogRow_1 end ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 end ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBOutput_1";

				try {
					if (batchSizeCounter_ExecutionLogStart_1_tDBOutput_1 != 0) {
						int countSum_ExecutionLogStart_1_tDBOutput_1 = 0;

						if (log.isDebugEnabled())
							log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Executing the ") + ("INSERT")
									+ (" batch."));
						for (int countEach_ExecutionLogStart_1_tDBOutput_1 : pstmt_ExecutionLogStart_1_tDBOutput_1
								.executeBatch()) {
							countSum_ExecutionLogStart_1_tDBOutput_1 += (countEach_ExecutionLogStart_1_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED
									? 0
									: 1);
						}
						rowsToCommitCount_ExecutionLogStart_1_tDBOutput_1 += countSum_ExecutionLogStart_1_tDBOutput_1;

						if (log.isDebugEnabled())
							log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("The ") + ("INSERT")
									+ (" batch execution has succeeded."));

						insertedCount_ExecutionLogStart_1_tDBOutput_1 += countSum_ExecutionLogStart_1_tDBOutput_1;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					throw (e);

				}
				batchSizeCounter_ExecutionLogStart_1_tDBOutput_1 = 0;

				if (pstmt_ExecutionLogStart_1_tDBOutput_1 != null) {

					pstmt_ExecutionLogStart_1_tDBOutput_1.close();
					resourceMap.remove("pstmt_ExecutionLogStart_1_tDBOutput_1");

				}

				resourceMap.put("statementClosed_ExecutionLogStart_1_tDBOutput_1", true);

				nb_line_deleted_ExecutionLogStart_1_tDBOutput_1 = nb_line_deleted_ExecutionLogStart_1_tDBOutput_1
						+ deletedCount_ExecutionLogStart_1_tDBOutput_1;
				nb_line_update_ExecutionLogStart_1_tDBOutput_1 = nb_line_update_ExecutionLogStart_1_tDBOutput_1
						+ updatedCount_ExecutionLogStart_1_tDBOutput_1;
				nb_line_inserted_ExecutionLogStart_1_tDBOutput_1 = nb_line_inserted_ExecutionLogStart_1_tDBOutput_1
						+ insertedCount_ExecutionLogStart_1_tDBOutput_1;
				nb_line_rejected_ExecutionLogStart_1_tDBOutput_1 = nb_line_rejected_ExecutionLogStart_1_tDBOutput_1
						+ rejectedCount_ExecutionLogStart_1_tDBOutput_1;

				globalMap.put("ExecutionLogStart_1_tDBOutput_1_NB_LINE", nb_line_ExecutionLogStart_1_tDBOutput_1);
				globalMap.put("ExecutionLogStart_1_tDBOutput_1_NB_LINE_UPDATED",
						nb_line_update_ExecutionLogStart_1_tDBOutput_1);
				globalMap.put("ExecutionLogStart_1_tDBOutput_1_NB_LINE_INSERTED",
						nb_line_inserted_ExecutionLogStart_1_tDBOutput_1);
				globalMap.put("ExecutionLogStart_1_tDBOutput_1_NB_LINE_DELETED",
						nb_line_deleted_ExecutionLogStart_1_tDBOutput_1);
				globalMap.put("ExecutionLogStart_1_tDBOutput_1_NB_LINE_REJECTED",
						nb_line_rejected_ExecutionLogStart_1_tDBOutput_1);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId,
						"ExecutionLogStart_1_row2", 2, 0, "ExecutionLogStart_1_tLogRow_1", "tLogRow_1", "tLogRow",
						"ExecutionLogStart_1_tDBOutput_1", "tDBOutput_1", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("ExecutionLogStart_1_tDBOutput_1 - " + ("Done."));

				ok_Hash.put("ExecutionLogStart_1_tDBOutput_1", true);
				end_Hash.put("ExecutionLogStart_1_tDBOutput_1", System.currentTimeMillis());

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [ExecutionLogStart_1_tRowGenerator_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tRowGenerator_1";

				/**
				 * [ExecutionLogStart_1_tRowGenerator_1 finally ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tMap_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tMap_1";

				/**
				 * [ExecutionLogStart_1_tMap_1 finally ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tLogRow_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tLogRow_1";

				/**
				 * [ExecutionLogStart_1_tLogRow_1 finally ] stop
				 */

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 finally ] start
				 */

				currentComponent = "ExecutionLogStart_1_tDBOutput_1";

				if (resourceMap.get("statementClosed_ExecutionLogStart_1_tDBOutput_1") == null) {
					java.sql.PreparedStatement pstmtToClose_ExecutionLogStart_1_tDBOutput_1 = null;
					if ((pstmtToClose_ExecutionLogStart_1_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_ExecutionLogStart_1_tDBOutput_1")) != null) {
						pstmtToClose_ExecutionLogStart_1_tDBOutput_1.close();
					}
				}

				/**
				 * [ExecutionLogStart_1_tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("ExecutionLogStart_1_tRowGenerator_1_SUBPROCESS_STATE", 1);
	}

	public static class out1Struct implements routines.system.IPersistableRow<out1Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Boolean Product_NameIsNullable() {
			return true;
		}

		public Boolean Product_NameIsKey() {
			return false;
		}

		public Integer Product_NameLength() {
			return 100;
		}

		public Integer Product_NamePrecision() {
			return 0;
		}

		public String Product_NameDefault() {

			return null;

		}

		public String Product_NameComment() {

			return "";

		}

		public String Product_NamePattern() {

			return "";

		}

		public String Product_NameOriginalDbColumnName() {

			return "Product_Name";

		}

		public Integer product_id;

		public Integer getProduct_id() {
			return this.product_id;
		}

		public Boolean product_idIsNullable() {
			return true;
		}

		public Boolean product_idIsKey() {
			return false;
		}

		public Integer product_idLength() {
			return null;
		}

		public Integer product_idPrecision() {
			return null;
		}

		public String product_idDefault() {

			return null;

		}

		public String product_idComment() {

			return "";

		}

		public String product_idPattern() {

			return "";

		}

		public String product_idOriginalDbColumnName() {

			return "product_id";

		}

		public java.util.Date ETL_Load_Date;

		public java.util.Date getETL_Load_Date() {
			return this.ETL_Load_Date;
		}

		public Boolean ETL_Load_DateIsNullable() {
			return true;
		}

		public Boolean ETL_Load_DateIsKey() {
			return false;
		}

		public Integer ETL_Load_DateLength() {
			return null;
		}

		public Integer ETL_Load_DatePrecision() {
			return null;
		}

		public String ETL_Load_DateDefault() {

			return null;

		}

		public String ETL_Load_DateComment() {

			return "";

		}

		public String ETL_Load_DatePattern() {

			return "dd-MM-yyyy";

		}

		public String ETL_Load_DateOriginalDbColumnName() {

			return "ETL_Load_Date";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

					this.product_id = readInteger(dis);

					this.ETL_Load_Date = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

					this.product_id = readInteger(dis);

					this.ETL_Load_Date = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

				// Integer

				writeInteger(this.product_id, dos);

				// java.util.Date

				writeDate(this.ETL_Load_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

				// Integer

				writeInteger(this.product_id, dos);

				// java.util.Date

				writeDate(this.ETL_Load_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_Name=" + Product_Name);
			sb.append(",product_id=" + String.valueOf(product_id));
			sb.append(",ETL_Load_Date=" + String.valueOf(ETL_Load_Date));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(product_id);
			}

			sb.append("|");

			if (ETL_Load_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(ETL_Load_Date);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Boolean Product_NameIsNullable() {
			return true;
		}

		public Boolean Product_NameIsKey() {
			return false;
		}

		public Integer Product_NameLength() {
			return 100;
		}

		public Integer Product_NamePrecision() {
			return 0;
		}

		public String Product_NameDefault() {

			return null;

		}

		public String Product_NameComment() {

			return "";

		}

		public String Product_NamePattern() {

			return "";

		}

		public String Product_NameOriginalDbColumnName() {

			return "Product_Name";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_Name=" + Product_Name);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Boolean Product_NameIsNullable() {
			return true;
		}

		public Boolean Product_NameIsKey() {
			return false;
		}

		public Integer Product_NameLength() {
			return 100;
		}

		public Integer Product_NamePrecision() {
			return 0;
		}

		public String Product_NameDefault() {

			return null;

		}

		public String Product_NameComment() {

			return "";

		}

		public String Product_NamePattern() {

			return "";

		}

		public String Product_NameOriginalDbColumnName() {

			return "Product_Name";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_Name=" + Product_Name);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tDBInput_1Struct implements routines.system.IPersistableRow<after_tDBInput_1Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Boolean Product_NameIsNullable() {
			return true;
		}

		public Boolean Product_NameIsKey() {
			return false;
		}

		public Integer Product_NameLength() {
			return 100;
		}

		public Integer Product_NamePrecision() {
			return 0;
		}

		public String Product_NameDefault() {

			return null;

		}

		public String Product_NameComment() {

			return "";

		}

		public String Product_NamePattern() {

			return "";

		}

		public String Product_NameOriginalDbColumnName() {

			return "Product_Name";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_Name=" + Product_Name);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBInput_1");
		org.slf4j.MDC.put("_subJobPid", "Xk4ImT_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tDBInput_2Process(globalMap);

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				out1Struct out1 = new out1Struct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out1");

				int tos_count_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_1 = new StringBuilder();
							log4jParamters_tDBOutput_1.append("Parameters:");
							log4jParamters_tDBOutput_1.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"product_dimension\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "CREATE_IF_NOT_EXISTS");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_1 - " + (log4jParamters_tDBOutput_1));
						}
					}
					new BytesLimit65535_tDBOutput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_1", "tDBOutput_1", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				String tableName_tDBOutput_1 = "product_dimension";
				boolean whetherReject_tDBOutput_1 = false;

				java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
				calendar_tDBOutput_1.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				calendar_tDBOutput_1.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				long date_tDBOutput_1;

				java.sql.Connection conn_tDBOutput_1 = null;
				conn_tDBOutput_1 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_1.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_1.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_1.getAutoCommit()) + ("'."));

				int count_tDBOutput_1 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_1 = conn_tDBOutput_1.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_1 = dbMetaData_tDBOutput_1.getTables("real_time", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_1 = false;
				while (rsTable_tDBOutput_1.next()) {
					String table_tDBOutput_1 = rsTable_tDBOutput_1.getString("TABLE_NAME");
					if (table_tDBOutput_1.equalsIgnoreCase("product_dimension")) {
						whetherExist_tDBOutput_1 = true;
						break;
					}
				}
				if (!whetherExist_tDBOutput_1) {
					try (java.sql.Statement stmtCreate_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
						if (log.isDebugEnabled())
							log.debug(
									"tDBOutput_1 - " + ("Creating") + (" table '") + (tableName_tDBOutput_1) + ("'."));
						stmtCreate_tDBOutput_1.execute("CREATE TABLE `" + tableName_tDBOutput_1
								+ "`(`Product_Name` VARCHAR(100)  ,`product_id` INT(0)  ,`ETL_Load_Date` DATETIME )");
						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Create") + (" table '") + (tableName_tDBOutput_1)
									+ ("' has succeeded."));
					}
				}

				String insert_tDBOutput_1 = "INSERT INTO `" + "product_dimension"
						+ "` (`Product_Name`,`product_id`,`ETL_Load_Date`) VALUES (?,?,?)";

				int batchSize_tDBOutput_1 = 100;
				int batchSizeCounter_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_1 = new StringBuilder();
							log4jParamters_tMap_1.append("Parameters:");
							log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_1 - " + (log4jParamters_tMap_1));
						}
					}
					new BytesLimit65535_tMap_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row2_tMap_1 = 0;

				int count_row4_tMap_1 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) globalMap
						.get("tHash_Lookup_row4"));

				row4Struct row4HashKey = new row4Struct();
				row4Struct row4Default = new row4Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out1_tMap_1 = 0;

				out1Struct out1_tmp = new out1Struct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tUniqRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tUniqRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tUniqRow_1 = new StringBuilder();
							log4jParamters_tUniqRow_1.append("Parameters:");
							log4jParamters_tUniqRow_1.append("UNIQUE_KEY" + " = " + "[{CASE_SENSITIVE=" + ("false")
									+ ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Product_Name") + "}]");
							log4jParamters_tUniqRow_1.append(" | ");
							log4jParamters_tUniqRow_1.append("ONLY_ONCE_EACH_DUPLICATED_KEY" + " = " + "false");
							log4jParamters_tUniqRow_1.append(" | ");
							log4jParamters_tUniqRow_1.append("IS_VIRTUAL_COMPONENT" + " = " + "false");
							log4jParamters_tUniqRow_1.append(" | ");
							log4jParamters_tUniqRow_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "false");
							log4jParamters_tUniqRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tUniqRow_1 - " + (log4jParamters_tUniqRow_1));
						}
					}
					new BytesLimit65535_tUniqRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tUniqRow_1", "tUniqRow_1", "tUniqRow");
					talendJobLogProcess(globalMap);
				}

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String Product_Name;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.Product_Name == null) ? 0 : this.Product_Name.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.Product_Name == null) {
							if (other.Product_Name != null)
								return false;

						} else if (!this.Product_Name.equals(other.Product_Name))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				log.debug("tUniqRow_1 - Start to process the data from datasource.");
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_1 = new StringBuilder();
							log4jParamters_tDBInput_1.append("Parameters:");
							log4jParamters_tDBInput_1.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TABLE" + " = " + "\"stage\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERY" + " = " + "\"select Product_Name from stage\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Product_Name") + "}]");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
							log4jParamters_tDBInput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_1 - " + (log4jParamters_tDBInput_1));
						}
					}
					new BytesLimit65535_tDBInput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_1", "tDBInput_1", "tMysqlInput");
					talendJobLogProcess(globalMap);
				}

				java.util.Calendar calendar_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_1 = calendar_tDBInput_1.getTime();
				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				conn_tDBInput_1 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_tDBInput_1 != null) {
					if (conn_tDBInput_1.getMetaData() != null) {

						log.debug("tDBInput_1 - Uses an existing connection with username '"
								+ conn_tDBInput_1.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_1.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "select Product_Name from stage";

				log.debug("tDBInput_1 - Executing the query: '" + dbquery_tDBInput_1 + "'.");

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);

				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					log.debug("tDBInput_1 - Retrieving records from the database.");

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.Product_Name = null;
						} else {

							row1.Product_Name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}

						log.debug("tDBInput_1 - Retrieving the record " + nb_line_tDBInput_1 + ".");

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tUniqRow_1 main ] start
						 */

						currentComponent = "tUniqRow_1";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "tDBInput_1", "tMysqlInput", "tUniqRow_1", "tUniqRow_1",
								"tUniqRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
						}

						row2 = null;
						if (row1.Product_Name == null) {
							finder_tUniqRow_1.Product_Name = null;
						} else {
							finder_tUniqRow_1.Product_Name = row1.Product_Name.toLowerCase();
						}
						finder_tUniqRow_1.hashCodeDirty = true;
						if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
							KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

							if (row1.Product_Name == null) {
								new_tUniqRow_1.Product_Name = null;
							} else {
								new_tUniqRow_1.Product_Name = row1.Product_Name.toLowerCase();
							}

							keystUniqRow_1.add(new_tUniqRow_1);
							if (row2 == null) {

								log.trace("tUniqRow_1 - Writing the unique record " + (nb_uniques_tUniqRow_1 + 1)
										+ " into row2.");

								row2 = new row2Struct();
							}
							row2.Product_Name = row1.Product_Name;
							nb_uniques_tUniqRow_1++;
						} else {
							nb_duplicates_tUniqRow_1++;
						}

						tos_count_tUniqRow_1++;

						/**
						 * [tUniqRow_1 main ] stop
						 */

						/**
						 * [tUniqRow_1 process_data_begin ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_begin ] stop
						 */
// Start of branch "row2"
						if (row2 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row2", "tUniqRow_1", "tUniqRow_1", "tUniqRow", "tMap_1", "tMap_1", "tMap"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							row4Struct row4 = null;

							// ###############################
							// # Input tables (lookups)

							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							///////////////////////////////////////////////
							// Starting Lookup Table "row4"
							///////////////////////////////////////////////

							boolean forceLooprow4 = false;

							row4Struct row4ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								row4HashKey.Product_Name = row2.Product_Name;

								row4HashKey.hashCodeDirty = true;

								tHash_Lookup_row4.lookup(row4HashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row4 != null && tHash_Lookup_row4.getCount(row4HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row4'
								// and it contains more one result from keys : row4.Product_Name = '" +
								// row4HashKey.Product_Name + "'");
							} // G 071

							row4Struct fromLookup_row4 = null;
							row4 = row4Default;

							if (tHash_Lookup_row4 != null && tHash_Lookup_row4.hasNext()) { // G 099

								fromLookup_row4 = tHash_Lookup_row4.next();

							} // G 099

							if (fromLookup_row4 != null) {
								row4 = fromLookup_row4;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								out1 = null;

// # Output table : 'out1'
// # Filter conditions 
								if (

								Relational.ISNULL(row4.Product_Name)

								) {
									count_out1_tMap_1++;

									out1_tmp.Product_Name = row2.Product_Name;
									out1_tmp.product_id = Numeric.sequence("s1", 1, 1);
									out1_tmp.ETL_Load_Date = TalendDate.getCurrentDate();
									out1 = out1_tmp;
									log.debug("tMap_1 - Outputting the record " + count_out1_tMap_1
											+ " of the output table 'out1'.");

								} // closing filter/reject
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "out1"
							if (out1 != null) {

								/**
								 * [tDBOutput_1 main ] start
								 */

								currentComponent = "tDBOutput_1";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "out1", "tMap_1", "tMap_1", "tMap", "tDBOutput_1", "tDBOutput_1",
										"tMysqlOutput"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("out1 - " + (out1 == null ? "" : out1.toLogString()));
								}

								whetherReject_tDBOutput_1 = false;
								if (out1.Product_Name == null) {
									pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(1, out1.Product_Name);
								}

								if (out1.product_id == null) {
									pstmt_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(2, out1.product_id);
								}

								if (out1.ETL_Load_Date != null) {
									date_tDBOutput_1 = out1.ETL_Load_Date.getTime();
									if (date_tDBOutput_1 < year1_tDBOutput_1
											|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
										pstmt_tDBOutput_1.setString(3, "0000-00-00 00:00:00");
									} else {
										pstmt_tDBOutput_1.setTimestamp(3, new java.sql.Timestamp(date_tDBOutput_1));
									}
								} else {
									pstmt_tDBOutput_1.setNull(3, java.sql.Types.DATE);
								}

								pstmt_tDBOutput_1.addBatch();
								nb_line_tDBOutput_1++;

								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Adding the record ") + (nb_line_tDBOutput_1)
											+ (" to the ") + ("INSERT") + (" batch."));
								batchSizeCounter_tDBOutput_1++;
								if (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1) {
									try {
										int countSum_tDBOutput_1 = 0;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
										for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED
													? 0
													: 1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("The ") + ("INSERT")
													+ (" batch execution has succeeded."));
										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
									} catch (java.sql.BatchUpdateException e) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
										throw (e);
									}

									batchSizeCounter_tDBOutput_1 = 0;
								}

								tos_count_tDBOutput_1++;

								/**
								 * [tDBOutput_1 main ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_end ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_end ] stop
								 */

							} // End of branch "out1"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row2"

						/**
						 * [tUniqRow_1 process_data_end ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
				log.debug("tDBInput_1 - Retrieved records count: " + nb_line_tDBInput_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Done."));

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);
				log.info("tUniqRow_1 - Unique records count: " + (nb_uniques_tUniqRow_1) + " .");
				log.info("tUniqRow_1 - Duplicate records count: " + (nb_duplicates_tUniqRow_1) + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "tDBInput_1", "tMysqlInput", "tUniqRow_1", "tUniqRow_1", "tUniqRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1 - " + ("Done."));

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row4 != null) {
					tHash_Lookup_row4.endGet();
				}
				globalMap.remove("tHash_Lookup_row4");

// ###############################      
				log.debug("tMap_1 - Written records count in the table 'out1': " + count_out1_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tUniqRow_1", "tUniqRow_1", "tUniqRow", "tMap_1", "tMap_1", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Done."));

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (batchSizeCounter_tDBOutput_1 != 0) {
						int countSum_tDBOutput_1 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					throw (e);

				}
				batchSizeCounter_tDBOutput_1 = 0;

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");

				}

				resourceMap.put("statementClosed_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out1", 2, 0, "tMap_1",
						"tMap_1", "tMap", "tDBOutput_1", "tDBOutput_1", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Done."));

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBInput_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tRowGenerator_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row4");

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
					if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_1")) != null) {
						pstmtToClose_tDBOutput_1.close();
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class outStruct implements routines.system.IPersistableRow<outStruct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date job_end_time;

		public java.util.Date getJob_end_time() {
			return this.job_end_time;
		}

		public Boolean job_end_timeIsNullable() {
			return true;
		}

		public Boolean job_end_timeIsKey() {
			return false;
		}

		public Integer job_end_timeLength() {
			return null;
		}

		public Integer job_end_timePrecision() {
			return null;
		}

		public String job_end_timeDefault() {

			return null;

		}

		public String job_end_timeComment() {

			return "";

		}

		public String job_end_timePattern() {

			return "dd-MM-yyyy";

		}

		public String job_end_timeOriginalDbColumnName() {

			return "job_end_time";

		}

		public String job_end_status;

		public String getJob_end_status() {
			return this.job_end_status;
		}

		public Boolean job_end_statusIsNullable() {
			return true;
		}

		public Boolean job_end_statusIsKey() {
			return false;
		}

		public Integer job_end_statusLength() {
			return null;
		}

		public Integer job_end_statusPrecision() {
			return null;
		}

		public String job_end_statusDefault() {

			return null;

		}

		public String job_end_statusComment() {

			return "";

		}

		public String job_end_statusPattern() {

			return "";

		}

		public String job_end_statusOriginalDbColumnName() {

			return "job_end_status";

		}

		public Integer execution_ID;

		public Integer getExecution_ID() {
			return this.execution_ID;
		}

		public Boolean execution_IDIsNullable() {
			return true;
		}

		public Boolean execution_IDIsKey() {
			return true;
		}

		public Integer execution_IDLength() {
			return null;
		}

		public Integer execution_IDPrecision() {
			return null;
		}

		public String execution_IDDefault() {

			return null;

		}

		public String execution_IDComment() {

			return "";

		}

		public String execution_IDPattern() {

			return "";

		}

		public String execution_IDOriginalDbColumnName() {

			return "execution_ID";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.execution_ID == null) ? 0 : this.execution_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final outStruct other = (outStruct) obj;

			if (this.execution_ID == null) {
				if (other.execution_ID != null)
					return false;

			} else if (!this.execution_ID.equals(other.execution_ID))

				return false;

			return true;
		}

		public void copyDataTo(outStruct other) {

			other.job_end_time = this.job_end_time;
			other.job_end_status = this.job_end_status;
			other.execution_ID = this.execution_ID;

		}

		public void copyKeysDataTo(outStruct other) {

			other.execution_ID = this.execution_ID;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.job_end_time = readDate(dis);

					this.job_end_status = readString(dis);

					this.execution_ID = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.job_end_time = readDate(dis);

					this.job_end_status = readString(dis);

					this.execution_ID = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.job_end_time, dos);

				// String

				writeString(this.job_end_status, dos);

				// Integer

				writeInteger(this.execution_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.job_end_time, dos);

				// String

				writeString(this.job_end_status, dos);

				// Integer

				writeInteger(this.execution_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("job_end_time=" + String.valueOf(job_end_time));
			sb.append(",job_end_status=" + job_end_status);
			sb.append(",execution_ID=" + String.valueOf(execution_ID));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (job_end_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_time);
			}

			sb.append("|");

			if (job_end_status == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_status);
			}

			sb.append("|");

			if (execution_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(execution_ID);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(outStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.execution_ID, other.execution_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public String newColumn;

		public String getNewColumn() {
			return this.newColumn;
		}

		public Boolean newColumnIsNullable() {
			return true;
		}

		public Boolean newColumnIsKey() {
			return false;
		}

		public Integer newColumnLength() {
			return null;
		}

		public Integer newColumnPrecision() {
			return null;
		}

		public String newColumnDefault() {

			return "";

		}

		public String newColumnComment() {

			return "";

		}

		public String newColumnPattern() {

			return "";

		}

		public String newColumnOriginalDbColumnName() {

			return "newColumn";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.newColumn, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.newColumn, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("newColumn=" + newColumn);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (newColumn == null) {
				sb.append("<null>");
			} else {
				sb.append(newColumn);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tRowGenerator_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tRowGenerator_1");
		org.slf4j.MDC.put("_subJobPid", "P9c9BJ_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();
				outStruct out = new outStruct();

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out");

				int tos_count_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_2 = new StringBuilder();
							log4jParamters_tDBOutput_2.append("Parameters:");
							log4jParamters_tDBOutput_2.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"Execution_Log_table\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "UPDATE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_2 - " + (log4jParamters_tDBOutput_2));
						}
					}
					new BytesLimit65535_tDBOutput_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_2", "tDBOutput_2", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int updateKeyCount_tDBOutput_2 = 1;
				if (updateKeyCount_tDBOutput_2 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_2 == 3 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				String tableName_tDBOutput_2 = "Execution_Log_table";
				boolean whetherReject_tDBOutput_2 = false;

				java.util.Calendar calendar_tDBOutput_2 = java.util.Calendar.getInstance();
				calendar_tDBOutput_2.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				calendar_tDBOutput_2.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				long date_tDBOutput_2;

				java.sql.Connection conn_tDBOutput_2 = null;
				conn_tDBOutput_2 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_2.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_2.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_2.getAutoCommit()) + ("'."));
				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;

				String update_tDBOutput_2 = "UPDATE `" + "Execution_Log_table"
						+ "` SET `job_end_time` = ?,`job_end_status` = ? WHERE `execution_ID` = ?";

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(update_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tMap_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_2 = new StringBuilder();
							log4jParamters_tMap_2.append("Parameters:");
							log4jParamters_tMap_2.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_2 - " + (log4jParamters_tMap_2));
						}
					}
					new BytesLimit65535_tMap_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_2", "tMap_2", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row3_tMap_2 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out_tMap_2 = 0;

				outStruct out_tmp = new outStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tRowGenerator_1 begin ] start
				 */

				ok_Hash.put("tRowGenerator_1", false);
				start_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				currentComponent = "tRowGenerator_1";

				int tos_count_tRowGenerator_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRowGenerator_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRowGenerator_1 = new StringBuilder();
							log4jParamters_tRowGenerator_1.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("tRowGenerator_1 - " + (log4jParamters_tRowGenerator_1));
						}
					}
					new BytesLimit65535_tRowGenerator_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRowGenerator_1", "tRowGenerator_1", "tRowGenerator");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tRowGenerator_1 = 0;
				int nb_max_row_tRowGenerator_1 = 1;

				class tRowGenerator_1Randomizer {
					public String getRandomnewColumn() {

						return TalendString.getAsciiRandomString(6);

					}
				}
				tRowGenerator_1Randomizer randtRowGenerator_1 = new tRowGenerator_1Randomizer();

				log.info("tRowGenerator_1 - Generating records.");
				for (int itRowGenerator_1 = 0; itRowGenerator_1 < nb_max_row_tRowGenerator_1; itRowGenerator_1++) {
					row3.newColumn = randtRowGenerator_1.getRandomnewColumn();
					nb_line_tRowGenerator_1++;

					log.debug("tRowGenerator_1 - Retrieving the record " + nb_line_tRowGenerator_1 + ".");

					/**
					 * [tRowGenerator_1 begin ] stop
					 */

					/**
					 * [tRowGenerator_1 main ] start
					 */

					currentComponent = "tRowGenerator_1";

					tos_count_tRowGenerator_1++;

					/**
					 * [tRowGenerator_1 main ] stop
					 */

					/**
					 * [tRowGenerator_1 process_data_begin ] start
					 */

					currentComponent = "tRowGenerator_1";

					/**
					 * [tRowGenerator_1 process_data_begin ] stop
					 */

					/**
					 * [tMap_2 main ] start
					 */

					currentComponent = "tMap_2";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row3", "tRowGenerator_1", "tRowGenerator_1", "tRowGenerator", "tMap_2", "tMap_2", "tMap"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
					}

					boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

					// ###############################
					// # Input tables (lookups)

					boolean rejectedInnerJoin_tMap_2 = false;
					boolean mainRowRejected_tMap_2 = false;
					// ###############################
					{ // start of Var scope

						// ###############################
						// # Vars tables

						Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
						// ###############################
						// # Output tables

						out = null;

// # Output table : 'out'
						count_out_tMap_2++;

						out_tmp.job_end_time = TalendDate.getCurrentDate();
						out_tmp.job_end_status = "End Success";
						out_tmp.execution_ID = context.exeID;
						out = out_tmp;
						log.debug("tMap_2 - Outputting the record " + count_out_tMap_2 + " of the output table 'out'.");

// ###############################

					} // end of Var scope

					rejectedInnerJoin_tMap_2 = false;

					tos_count_tMap_2++;

					/**
					 * [tMap_2 main ] stop
					 */

					/**
					 * [tMap_2 process_data_begin ] start
					 */

					currentComponent = "tMap_2";

					/**
					 * [tMap_2 process_data_begin ] stop
					 */
// Start of branch "out"
					if (out != null) {

						/**
						 * [tDBOutput_2 main ] start
						 */

						currentComponent = "tDBOutput_2";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "out", "tMap_2", "tMap_2", "tMap", "tDBOutput_2", "tDBOutput_2", "tMysqlOutput"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("out - " + (out == null ? "" : out.toLogString()));
						}

						whetherReject_tDBOutput_2 = false;
						if (out.job_end_time != null) {
							date_tDBOutput_2 = out.job_end_time.getTime();
							if (date_tDBOutput_2 < year1_tDBOutput_2 || date_tDBOutput_2 >= year10000_tDBOutput_2) {
								pstmt_tDBOutput_2.setString(1, "0000-00-00 00:00:00");
							} else {
								pstmt_tDBOutput_2.setTimestamp(1, new java.sql.Timestamp(date_tDBOutput_2));
							}
						} else {
							pstmt_tDBOutput_2.setNull(1, java.sql.Types.DATE);
						}

						if (out.job_end_status == null) {
							pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
						} else {
							pstmt_tDBOutput_2.setString(2, out.job_end_status);
						}

						if (out.execution_ID == null) {
							pstmt_tDBOutput_2.setNull(3 + count_tDBOutput_2, java.sql.Types.INTEGER);
						} else {
							pstmt_tDBOutput_2.setInt(3 + count_tDBOutput_2, out.execution_ID);
						}

						pstmt_tDBOutput_2.addBatch();
						nb_line_tDBOutput_2++;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Adding the record ") + (nb_line_tDBOutput_2) + (" to the ")
									+ ("UPDATE") + (" batch."));
						batchSizeCounter_tDBOutput_2++;
						if (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2) {
							try {
								int countSum_tDBOutput_2 = 0;
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Executing the ") + ("UPDATE") + (" batch."));
								for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
									countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
								}
								rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("The ") + ("UPDATE")
											+ (" batch execution has succeeded."));
								updatedCount_tDBOutput_2 += countSum_tDBOutput_2;
								batchSizeCounter_tDBOutput_2 = 0;
							} catch (java.sql.BatchUpdateException e) {
								globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
								int countSum_tDBOutput_2 = 0;
								for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
									countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
								}
								rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
								updatedCount_tDBOutput_2 += countSum_tDBOutput_2;
								System.err.println(e.getMessage());
								log.error("tDBOutput_2 - " + (e.getMessage()));
							}
						}

						tos_count_tDBOutput_2++;

						/**
						 * [tDBOutput_2 main ] stop
						 */

						/**
						 * [tDBOutput_2 process_data_begin ] start
						 */

						currentComponent = "tDBOutput_2";

						/**
						 * [tDBOutput_2 process_data_begin ] stop
						 */

						/**
						 * [tDBOutput_2 process_data_end ] start
						 */

						currentComponent = "tDBOutput_2";

						/**
						 * [tDBOutput_2 process_data_end ] stop
						 */

					} // End of branch "out"

					/**
					 * [tMap_2 process_data_end ] start
					 */

					currentComponent = "tMap_2";

					/**
					 * [tMap_2 process_data_end ] stop
					 */

					/**
					 * [tRowGenerator_1 process_data_end ] start
					 */

					currentComponent = "tRowGenerator_1";

					/**
					 * [tRowGenerator_1 process_data_end ] stop
					 */

					/**
					 * [tRowGenerator_1 end ] start
					 */

					currentComponent = "tRowGenerator_1";

				}
				globalMap.put("tRowGenerator_1_NB_LINE", nb_line_tRowGenerator_1);
				log.info("tRowGenerator_1 - Generated records count:" + nb_line_tRowGenerator_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Done."));

				ok_Hash.put("tRowGenerator_1", true);
				end_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				/**
				 * [tRowGenerator_1 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				currentComponent = "tMap_2";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_2 - Written records count in the table 'out': " + count_out_tMap_2 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tRowGenerator_1", "tRowGenerator_1", "tRowGenerator", "tMap_2", "tMap_2", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_2 - " + ("Done."));

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (pstmt_tDBOutput_2 != null) {
						int countSum_tDBOutput_2 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Executing the ") + ("UPDATE") + (" batch."));
						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("The ") + ("UPDATE") + (" batch execution has succeeded."));

						updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

					log.error("tDBOutput_2 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");

				}

				resourceMap.put("statementClosed_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out", 2, 0, "tMap_2",
						"tMap_2", "tMap", "tDBOutput_2", "tDBOutput_2", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Done."));

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRowGenerator_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tDBCommit_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRowGenerator_1 finally ] start
				 */

				currentComponent = "tRowGenerator_1";

				/**
				 * [tRowGenerator_1 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				currentComponent = "tMap_2";

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
					if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_2")) != null) {
						pstmtToClose_tDBOutput_2.close();
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 1);
	}

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBCommit_1");
		org.slf4j.MDC.put("_subJobPid", "KVDYyW_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBCommit_1 begin ] start
				 */

				ok_Hash.put("tDBCommit_1", false);
				start_Hash.put("tDBCommit_1", System.currentTimeMillis());

				currentComponent = "tDBCommit_1";

				int tos_count_tDBCommit_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBCommit_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBCommit_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBCommit_1 = new StringBuilder();
							log4jParamters_tDBCommit_1.append("Parameters:");
							log4jParamters_tDBCommit_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBCommit_1.append(" | ");
							log4jParamters_tDBCommit_1.append("CLOSE" + " = " + "true");
							log4jParamters_tDBCommit_1.append(" | ");
							log4jParamters_tDBCommit_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlCommit");
							log4jParamters_tDBCommit_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBCommit_1 - " + (log4jParamters_tDBCommit_1));
						}
					}
					new BytesLimit65535_tDBCommit_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBCommit_1", "tDBCommit_1", "tMysqlCommit");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tDBCommit_1 begin ] stop
				 */

				/**
				 * [tDBCommit_1 main ] start
				 */

				currentComponent = "tDBCommit_1";

				java.sql.Connection conn_tDBCommit_1 = (java.sql.Connection) globalMap
						.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_tDBCommit_1 != null && !conn_tDBCommit_1.isClosed()) {

					try {

						log.debug("tDBCommit_1 - Connection 'ExecutionLogStart_1_tDBConnection_1' starting to commit.");

						conn_tDBCommit_1.commit();

						log.debug(
								"tDBCommit_1 - Connection 'ExecutionLogStart_1_tDBConnection_1' commit has succeeded.");

					} finally {

						log.debug(
								"tDBCommit_1 - Closing the connection 'ExecutionLogStart_1_tDBConnection_1' to the database.");

						conn_tDBCommit_1.close();

						if ("com.mysql.cj.jdbc.Driver"
								.equals((String) globalMap.get("driverClass_ExecutionLogStart_1_tDBConnection_1"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug(
								"tDBCommit_1 - Connection 'ExecutionLogStart_1_tDBConnection_1' to the database closed.");

					}

				}

				tos_count_tDBCommit_1++;

				/**
				 * [tDBCommit_1 main ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_begin ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 process_data_begin ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_end ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 process_data_end ] stop
				 */

				/**
				 * [tDBCommit_1 end ] start
				 */

				currentComponent = "tDBCommit_1";

				if (log.isDebugEnabled())
					log.debug("tDBCommit_1 - " + ("Done."));

				ok_Hash.put("tDBCommit_1", true);
				end_Hash.put("tDBCommit_1", System.currentTimeMillis());

				/**
				 * [tDBCommit_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBCommit_1 finally ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 1);
	}

	public static class row4Struct implements routines.system.IPersistableComparableLookupRow<row4Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Boolean Product_NameIsNullable() {
			return true;
		}

		public Boolean Product_NameIsKey() {
			return false;
		}

		public Integer Product_NameLength() {
			return 100;
		}

		public Integer Product_NamePrecision() {
			return 0;
		}

		public String Product_NameDefault() {

			return null;

		}

		public String Product_NameComment() {

			return "";

		}

		public String Product_NamePattern() {

			return "";

		}

		public String Product_NameOriginalDbColumnName() {

			return "Product_Name";

		}

		public Integer product_id;

		public Integer getProduct_id() {
			return this.product_id;
		}

		public Boolean product_idIsNullable() {
			return true;
		}

		public Boolean product_idIsKey() {
			return false;
		}

		public Integer product_idLength() {
			return 10;
		}

		public Integer product_idPrecision() {
			return 0;
		}

		public String product_idDefault() {

			return "";

		}

		public String product_idComment() {

			return "";

		}

		public String product_idPattern() {

			return "";

		}

		public String product_idOriginalDbColumnName() {

			return "product_id";

		}

		public java.util.Date ETL_Load_Date;

		public java.util.Date getETL_Load_Date() {
			return this.ETL_Load_Date;
		}

		public Boolean ETL_Load_DateIsNullable() {
			return true;
		}

		public Boolean ETL_Load_DateIsKey() {
			return false;
		}

		public Integer ETL_Load_DateLength() {
			return 19;
		}

		public Integer ETL_Load_DatePrecision() {
			return 0;
		}

		public String ETL_Load_DateDefault() {

			return null;

		}

		public String ETL_Load_DateComment() {

			return "";

		}

		public String ETL_Load_DatePattern() {

			return "dd-MM-yyyy";

		}

		public String ETL_Load_DateOriginalDbColumnName() {

			return "ETL_Load_Date";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Product_Name == null) ? 0 : this.Product_Name.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row4Struct other = (row4Struct) obj;

			if (this.Product_Name == null) {
				if (other.Product_Name != null)
					return false;

			} else if (!this.Product_Name.equals(other.Product_Name))

				return false;

			return true;
		}

		public void copyDataTo(row4Struct other) {

			other.Product_Name = this.Product_Name;
			other.product_id = this.product_id;
			other.ETL_Load_Date = this.ETL_Load_Date;

		}

		public void copyKeysDataTo(row4Struct other) {

			other.Product_Name = this.Product_Name;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.Product_Name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Product_Name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.product_id = readInteger(dis, ois);

				this.ETL_Load_Date = readDate(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.product_id = readInteger(dis, objectIn);

				this.ETL_Load_Date = readDate(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.product_id, dos, oos);

				writeDate(this.ETL_Load_Date, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.product_id, dos, objectOut);

				writeDate(this.ETL_Load_Date, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_Name=" + Product_Name);
			sb.append(",product_id=" + String.valueOf(product_id));
			sb.append(",ETL_Load_Date=" + String.valueOf(ETL_Load_Date));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(product_id);
			}

			sb.append("|");

			if (ETL_Load_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(ETL_Load_Date);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Product_Name, other.Product_Name);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBInput_2");
		org.slf4j.MDC.put("_subJobPid", "quyhC3_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();

				/**
				 * [tAdvancedHash_row4 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row4", false);
				start_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row4";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row4");

				int tos_count_tAdvancedHash_row4 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row4", "tAdvancedHash_row4", "tAdvancedHash");
					talendJobLogProcess(globalMap);
				}

				// connection name:row4
				// source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row4,row4) |
				// target node:tAdvancedHash_row4 - inputs:(row4) outputs:()
				// linked node: tMap_1 - inputs:(row2,row4) outputs:(out1)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row4 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row4Struct>getLookup(matchingModeEnum_row4);

				globalMap.put("tHash_Lookup_row4", tHash_Lookup_row4);

				/**
				 * [tAdvancedHash_row4 begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_2 = new StringBuilder();
							log4jParamters_tDBInput_2.append("Parameters:");
							log4jParamters_tDBInput_2.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TABLE" + " = " + "\"product_dimension\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERY" + " = " + "\"select * from product_dimension\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Product_Name") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("product_id") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("ETL_Load_Date") + "}]");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
							log4jParamters_tDBInput_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_2 - " + (log4jParamters_tDBInput_2));
						}
					}
					new BytesLimit65535_tDBInput_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_2", "tDBInput_2", "tMysqlInput");
					talendJobLogProcess(globalMap);
				}

				java.util.Calendar calendar_tDBInput_2 = java.util.Calendar.getInstance();
				calendar_tDBInput_2.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_2 = calendar_tDBInput_2.getTime();
				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				conn_tDBInput_2 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_tDBInput_2 != null) {
					if (conn_tDBInput_2.getMetaData() != null) {

						log.debug("tDBInput_2 - Uses an existing connection with username '"
								+ conn_tDBInput_2.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_2.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "select * from product_dimension";

				log.debug("tDBInput_2 - Executing the query: '" + dbquery_tDBInput_2 + "'.");

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);

				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					log.debug("tDBInput_2 - Retrieving records from the database.");

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row4.Product_Name = null;
						} else {

							row4.Product_Name = routines.system.JDBCUtil.getString(rs_tDBInput_2, 1, false);
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row4.product_id = null;
						} else {

							row4.product_id = rs_tDBInput_2.getInt(2);
							if (rs_tDBInput_2.wasNull()) {
								row4.product_id = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row4.ETL_Load_Date = null;
						} else {

							if (rs_tDBInput_2.getString(3) != null) {
								String dateString_tDBInput_2 = rs_tDBInput_2.getString(3);
								if (!("0000-00-00").equals(dateString_tDBInput_2)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_2)) {
									row4.ETL_Load_Date = rs_tDBInput_2.getTimestamp(3);
								} else {
									row4.ETL_Load_Date = (java.util.Date) year0_tDBInput_2.clone();
								}
							} else {
								row4.ETL_Load_Date = null;
							}
						}

						log.debug("tDBInput_2 - Retrieving the record " + nb_line_tDBInput_2 + ".");

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						currentComponent = "tDBInput_2";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 main ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row4", "tDBInput_2", "tDBInput_2", "tMysqlInput", "tAdvancedHash_row4",
								"tAdvancedHash_row4", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row4 - " + (row4 == null ? "" : row4.toLogString()));
						}

						row4Struct row4_HashRow = new row4Struct();

						row4_HashRow.Product_Name = row4.Product_Name;

						row4_HashRow.product_id = row4.product_id;

						row4_HashRow.ETL_Load_Date = row4.ETL_Load_Date;

						tHash_Lookup_row4.put(row4_HashRow);

						tos_count_tAdvancedHash_row4++;

						/**
						 * [tAdvancedHash_row4 main ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						currentComponent = "tDBInput_2";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
				}
				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);
				log.debug("tDBInput_2 - Retrieved records count: " + nb_line_tDBInput_2 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_2 - " + ("Done."));

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row4 end ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				tHash_Lookup_row4.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row4", 2, 0,
						"tDBInput_2", "tDBInput_2", "tMysqlInput", "tAdvancedHash_row4", "tAdvancedHash_row4",
						"tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row4", true);
				end_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row4 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row4 finally ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				/**
				 * [tAdvancedHash_row4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class out7Struct implements routines.system.IPersistableRow<out7Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer execution_ID;

		public Integer getExecution_ID() {
			return this.execution_ID;
		}

		public Boolean execution_IDIsNullable() {
			return true;
		}

		public Boolean execution_IDIsKey() {
			return true;
		}

		public Integer execution_IDLength() {
			return null;
		}

		public Integer execution_IDPrecision() {
			return null;
		}

		public String execution_IDDefault() {

			return null;

		}

		public String execution_IDComment() {

			return "";

		}

		public String execution_IDPattern() {

			return "";

		}

		public String execution_IDOriginalDbColumnName() {

			return "execution_ID";

		}

		public String process_ID;

		public String getProcess_ID() {
			return this.process_ID;
		}

		public Boolean process_IDIsNullable() {
			return true;
		}

		public Boolean process_IDIsKey() {
			return false;
		}

		public Integer process_IDLength() {
			return null;
		}

		public Integer process_IDPrecision() {
			return null;
		}

		public String process_IDDefault() {

			return null;

		}

		public String process_IDComment() {

			return "";

		}

		public String process_IDPattern() {

			return "";

		}

		public String process_IDOriginalDbColumnName() {

			return "process_ID";

		}

		public String job_end_status;

		public String getJob_end_status() {
			return this.job_end_status;
		}

		public Boolean job_end_statusIsNullable() {
			return true;
		}

		public Boolean job_end_statusIsKey() {
			return false;
		}

		public Integer job_end_statusLength() {
			return null;
		}

		public Integer job_end_statusPrecision() {
			return null;
		}

		public String job_end_statusDefault() {

			return null;

		}

		public String job_end_statusComment() {

			return "";

		}

		public String job_end_statusPattern() {

			return "";

		}

		public String job_end_statusOriginalDbColumnName() {

			return "job_end_status";

		}

		public java.util.Date job_end_time;

		public java.util.Date getJob_end_time() {
			return this.job_end_time;
		}

		public Boolean job_end_timeIsNullable() {
			return true;
		}

		public Boolean job_end_timeIsKey() {
			return false;
		}

		public Integer job_end_timeLength() {
			return null;
		}

		public Integer job_end_timePrecision() {
			return null;
		}

		public String job_end_timeDefault() {

			return null;

		}

		public String job_end_timeComment() {

			return "";

		}

		public String job_end_timePattern() {

			return "dd-MM-yyyy";

		}

		public String job_end_timeOriginalDbColumnName() {

			return "job_end_time";

		}

		public String error_message;

		public String getError_message() {
			return this.error_message;
		}

		public Boolean error_messageIsNullable() {
			return true;
		}

		public Boolean error_messageIsKey() {
			return false;
		}

		public Integer error_messageLength() {
			return null;
		}

		public Integer error_messagePrecision() {
			return null;
		}

		public String error_messageDefault() {

			return null;

		}

		public String error_messageComment() {

			return "";

		}

		public String error_messagePattern() {

			return "";

		}

		public String error_messageOriginalDbColumnName() {

			return "error_message";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.execution_ID == null) ? 0 : this.execution_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final out7Struct other = (out7Struct) obj;

			if (this.execution_ID == null) {
				if (other.execution_ID != null)
					return false;

			} else if (!this.execution_ID.equals(other.execution_ID))

				return false;

			return true;
		}

		public void copyDataTo(out7Struct other) {

			other.execution_ID = this.execution_ID;
			other.process_ID = this.process_ID;
			other.job_end_status = this.job_end_status;
			other.job_end_time = this.job_end_time;
			other.error_message = this.error_message;

		}

		public void copyKeysDataTo(out7Struct other) {

			other.execution_ID = this.execution_ID;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

					this.error_message = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.process_ID = readString(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

					this.error_message = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

				// String

				writeString(this.error_message, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.execution_ID, dos);

				// String

				writeString(this.process_ID, dos);

				// String

				writeString(this.job_end_status, dos);

				// java.util.Date

				writeDate(this.job_end_time, dos);

				// String

				writeString(this.error_message, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("execution_ID=" + String.valueOf(execution_ID));
			sb.append(",process_ID=" + process_ID);
			sb.append(",job_end_status=" + job_end_status);
			sb.append(",job_end_time=" + String.valueOf(job_end_time));
			sb.append(",error_message=" + error_message);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (execution_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(execution_ID);
			}

			sb.append("|");

			if (process_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(process_ID);
			}

			sb.append("|");

			if (job_end_status == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_status);
			}

			sb.append("|");

			if (job_end_time == null) {
				sb.append("<null>");
			} else {
				sb.append(job_end_time);
			}

			sb.append("|");

			if (error_message == null) {
				sb.append("<null>");
			} else {
				sb.append(error_message);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out7Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.execution_ID, other.execution_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row9Struct implements routines.system.IPersistableRow<row9Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_product_dimension = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_product_dimension = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public Boolean momentIsNullable() {
			return true;
		}

		public Boolean momentIsKey() {
			return false;
		}

		public Integer momentLength() {
			return 0;
		}

		public Integer momentPrecision() {
			return 0;
		}

		public String momentDefault() {

			return "";

		}

		public String momentComment() {

			return null;

		}

		public String momentPattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String momentOriginalDbColumnName() {

			return "moment";

		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public Boolean pidIsNullable() {
			return true;
		}

		public Boolean pidIsKey() {
			return false;
		}

		public Integer pidLength() {
			return 20;
		}

		public Integer pidPrecision() {
			return 0;
		}

		public String pidDefault() {

			return "";

		}

		public String pidComment() {

			return null;

		}

		public String pidPattern() {

			return null;

		}

		public String pidOriginalDbColumnName() {

			return "pid";

		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Boolean root_pidIsNullable() {
			return true;
		}

		public Boolean root_pidIsKey() {
			return false;
		}

		public Integer root_pidLength() {
			return 20;
		}

		public Integer root_pidPrecision() {
			return 0;
		}

		public String root_pidDefault() {

			return "";

		}

		public String root_pidComment() {

			return null;

		}

		public String root_pidPattern() {

			return null;

		}

		public String root_pidOriginalDbColumnName() {

			return "root_pid";

		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public Boolean father_pidIsNullable() {
			return true;
		}

		public Boolean father_pidIsKey() {
			return false;
		}

		public Integer father_pidLength() {
			return 20;
		}

		public Integer father_pidPrecision() {
			return 0;
		}

		public String father_pidDefault() {

			return "";

		}

		public String father_pidComment() {

			return null;

		}

		public String father_pidPattern() {

			return null;

		}

		public String father_pidOriginalDbColumnName() {

			return "father_pid";

		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public Boolean projectIsNullable() {
			return true;
		}

		public Boolean projectIsKey() {
			return false;
		}

		public Integer projectLength() {
			return 50;
		}

		public Integer projectPrecision() {
			return 0;
		}

		public String projectDefault() {

			return "";

		}

		public String projectComment() {

			return null;

		}

		public String projectPattern() {

			return null;

		}

		public String projectOriginalDbColumnName() {

			return "project";

		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public Boolean jobIsNullable() {
			return true;
		}

		public Boolean jobIsKey() {
			return false;
		}

		public Integer jobLength() {
			return 255;
		}

		public Integer jobPrecision() {
			return 0;
		}

		public String jobDefault() {

			return "";

		}

		public String jobComment() {

			return null;

		}

		public String jobPattern() {

			return null;

		}

		public String jobOriginalDbColumnName() {

			return "job";

		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public Boolean contextIsNullable() {
			return true;
		}

		public Boolean contextIsKey() {
			return false;
		}

		public Integer contextLength() {
			return 50;
		}

		public Integer contextPrecision() {
			return 0;
		}

		public String contextDefault() {

			return "";

		}

		public String contextComment() {

			return null;

		}

		public String contextPattern() {

			return null;

		}

		public String contextOriginalDbColumnName() {

			return "context";

		}

		public Integer priority;

		public Integer getPriority() {
			return this.priority;
		}

		public Boolean priorityIsNullable() {
			return true;
		}

		public Boolean priorityIsKey() {
			return false;
		}

		public Integer priorityLength() {
			return 3;
		}

		public Integer priorityPrecision() {
			return 0;
		}

		public String priorityDefault() {

			return "";

		}

		public String priorityComment() {

			return null;

		}

		public String priorityPattern() {

			return null;

		}

		public String priorityOriginalDbColumnName() {

			return "priority";

		}

		public String type;

		public String getType() {
			return this.type;
		}

		public Boolean typeIsNullable() {
			return true;
		}

		public Boolean typeIsKey() {
			return false;
		}

		public Integer typeLength() {
			return 255;
		}

		public Integer typePrecision() {
			return 0;
		}

		public String typeDefault() {

			return "";

		}

		public String typeComment() {

			return null;

		}

		public String typePattern() {

			return null;

		}

		public String typeOriginalDbColumnName() {

			return "type";

		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public Boolean originIsNullable() {
			return true;
		}

		public Boolean originIsKey() {
			return false;
		}

		public Integer originLength() {
			return 255;
		}

		public Integer originPrecision() {
			return 0;
		}

		public String originDefault() {

			return "";

		}

		public String originComment() {

			return null;

		}

		public String originPattern() {

			return null;

		}

		public String originOriginalDbColumnName() {

			return "origin";

		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Boolean messageIsNullable() {
			return true;
		}

		public Boolean messageIsKey() {
			return false;
		}

		public Integer messageLength() {
			return 255;
		}

		public Integer messagePrecision() {
			return 0;
		}

		public String messageDefault() {

			return "";

		}

		public String messageComment() {

			return null;

		}

		public String messagePattern() {

			return null;

		}

		public String messageOriginalDbColumnName() {

			return "message";

		}

		public Integer code;

		public Integer getCode() {
			return this.code;
		}

		public Boolean codeIsNullable() {
			return true;
		}

		public Boolean codeIsKey() {
			return false;
		}

		public Integer codeLength() {
			return 3;
		}

		public Integer codePrecision() {
			return 0;
		}

		public String codeDefault() {

			return "";

		}

		public String codeComment() {

			return null;

		}

		public String codePattern() {

			return null;

		}

		public String codeOriginalDbColumnName() {

			return "code";

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_product_dimension.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_product_dimension.length == 0) {
						commonByteArray_FINALPROJECT_product_dimension = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_product_dimension = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_product_dimension, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_product_dimension, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.root_pid = readString(dis);

					this.father_pid = readString(dis);

					this.project = readString(dis);

					this.job = readString(dis);

					this.context = readString(dis);

					this.priority = readInteger(dis);

					this.type = readString(dis);

					this.origin = readString(dis);

					this.message = readString(dis);

					this.code = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_product_dimension) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.root_pid = readString(dis);

					this.father_pid = readString(dis);

					this.project = readString(dis);

					this.job = readString(dis);

					this.context = readString(dis);

					this.priority = readInteger(dis);

					this.type = readString(dis);

					this.origin = readString(dis);

					this.message = readString(dis);

					this.code = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.root_pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.context, dos);

				// Integer

				writeInteger(this.priority, dos);

				// String

				writeString(this.type, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message, dos);

				// Integer

				writeInteger(this.code, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.root_pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.context, dos);

				// Integer

				writeInteger(this.priority, dos);

				// String

				writeString(this.type, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message, dos);

				// Integer

				writeInteger(this.code, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",context=" + context);
			sb.append(",priority=" + String.valueOf(priority));
			sb.append(",type=" + type);
			sb.append(",origin=" + origin);
			sb.append(",message=" + message);
			sb.append(",code=" + String.valueOf(code));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (priority == null) {
				sb.append("<null>");
			} else {
				sb.append(priority);
			}

			sb.append("|");

			if (type == null) {
				sb.append("<null>");
			} else {
				sb.append(type);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (code == null) {
				sb.append("<null>");
			} else {
				sb.append(code);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row9Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tLogCatcher_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tLogCatcher_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tLogCatcher_1");
		org.slf4j.MDC.put("_subJobPid", "8pA0LY_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row9Struct row9 = new row9Struct();
				out7Struct out7 = new out7Struct();

				/**
				 * [tDBOutput_3 begin ] start
				 */

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out7");

				int tos_count_tDBOutput_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_3 = new StringBuilder();
							log4jParamters_tDBOutput_3.append("Parameters:");
							log4jParamters_tDBOutput_3.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("TABLE" + " = " + "\"Execution_Log_Table\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DATA_ACTION" + " = " + "UPDATE");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_3 - " + (log4jParamters_tDBOutput_3));
						}
					}
					new BytesLimit65535_tDBOutput_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_3", "tDBOutput_3", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int updateKeyCount_tDBOutput_3 = 1;
				if (updateKeyCount_tDBOutput_3 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_3 == 5 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
				}

				int nb_line_tDBOutput_3 = 0;
				int nb_line_update_tDBOutput_3 = 0;
				int nb_line_inserted_tDBOutput_3 = 0;
				int nb_line_deleted_tDBOutput_3 = 0;
				int nb_line_rejected_tDBOutput_3 = 0;

				int deletedCount_tDBOutput_3 = 0;
				int updatedCount_tDBOutput_3 = 0;
				int insertedCount_tDBOutput_3 = 0;
				int rowsToCommitCount_tDBOutput_3 = 0;
				int rejectedCount_tDBOutput_3 = 0;

				String tableName_tDBOutput_3 = "Execution_Log_Table";
				boolean whetherReject_tDBOutput_3 = false;

				java.util.Calendar calendar_tDBOutput_3 = java.util.Calendar.getInstance();
				calendar_tDBOutput_3.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				calendar_tDBOutput_3.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				long date_tDBOutput_3;

				java.sql.Connection conn_tDBOutput_3 = null;
				conn_tDBOutput_3 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_3.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_3.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_3.getAutoCommit()) + ("'."));
				int batchSize_tDBOutput_3 = 10000;
				int batchSizeCounter_tDBOutput_3 = 0;

				int count_tDBOutput_3 = 0;

				String update_tDBOutput_3 = "UPDATE `" + "Execution_Log_Table"
						+ "` SET `process_ID` = ?,`job_end_status` = ?,`job_end_time` = ?,`error_message` = ? WHERE `execution_ID` = ?";

				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(update_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row9");

				int tos_count_tMap_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_3 = new StringBuilder();
							log4jParamters_tMap_3.append("Parameters:");
							log4jParamters_tMap_3.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_3 - " + (log4jParamters_tMap_3));
						}
					}
					new BytesLimit65535_tMap_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_3", "tMap_3", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row9_tMap_3 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out7_tMap_3 = 0;

				out7Struct out7_tmp = new out7Struct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tLogCatcher_1 begin ] start
				 */

				ok_Hash.put("tLogCatcher_1", false);
				start_Hash.put("tLogCatcher_1", System.currentTimeMillis());

				currentComponent = "tLogCatcher_1";

				int tos_count_tLogCatcher_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogCatcher_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogCatcher_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogCatcher_1 = new StringBuilder();
							log4jParamters_tLogCatcher_1.append("Parameters:");
							log4jParamters_tLogCatcher_1.append("CATCH_JAVA_EXCEPTION" + " = " + "true");
							log4jParamters_tLogCatcher_1.append(" | ");
							log4jParamters_tLogCatcher_1.append("CATCH_TDIE" + " = " + "true");
							log4jParamters_tLogCatcher_1.append(" | ");
							log4jParamters_tLogCatcher_1.append("CATCH_TWARN" + " = " + "true");
							log4jParamters_tLogCatcher_1.append(" | ");
							log4jParamters_tLogCatcher_1.append("CATCH_TACTIONFAILURE" + " = " + "true");
							log4jParamters_tLogCatcher_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogCatcher_1 - " + (log4jParamters_tLogCatcher_1));
						}
					}
					new BytesLimit65535_tLogCatcher_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogCatcher_1", "tLogCatcher_1", "tLogCatcher");
					talendJobLogProcess(globalMap);
				}

				try {
					for (LogCatcherUtils.LogCatcherMessage lcm : tLogCatcher_1.getMessages()) {
						row9.type = lcm.getType();
						row9.origin = (lcm.getOrigin() == null || lcm.getOrigin().length() < 1 ? null
								: lcm.getOrigin());
						row9.priority = lcm.getPriority();
						row9.message = lcm.getMessage();
						row9.code = lcm.getCode();

						row9.moment = java.util.Calendar.getInstance().getTime();

						row9.pid = pid;
						row9.root_pid = rootPid;
						row9.father_pid = fatherPid;

						row9.project = projectName;
						row9.job = jobName;
						row9.context = contextStr;

						/**
						 * [tLogCatcher_1 begin ] stop
						 */

						/**
						 * [tLogCatcher_1 main ] start
						 */

						currentComponent = "tLogCatcher_1";

						tos_count_tLogCatcher_1++;

						/**
						 * [tLogCatcher_1 main ] stop
						 */

						/**
						 * [tLogCatcher_1 process_data_begin ] start
						 */

						currentComponent = "tLogCatcher_1";

						/**
						 * [tLogCatcher_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_3 main ] start
						 */

						currentComponent = "tMap_3";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row9", "tLogCatcher_1", "tLogCatcher_1", "tLogCatcher", "tMap_3", "tMap_3", "tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row9 - " + (row9 == null ? "" : row9.toLogString()));
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

						// ###############################
						// # Input tables (lookups)

						boolean rejectedInnerJoin_tMap_3 = false;
						boolean mainRowRejected_tMap_3 = false;
						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
							// ###############################
							// # Output tables

							out7 = null;

// # Output table : 'out7'
							count_out7_tMap_3++;

							out7_tmp.execution_ID = context.exeID;
							out7_tmp.process_ID = row9.pid;
							out7_tmp.job_end_status = "End Fail";
							out7_tmp.job_end_time = TalendDate.getCurrentDate();
							out7_tmp.error_message = row9.message;
							out7 = out7_tmp;
							log.debug("tMap_3 - Outputting the record " + count_out7_tMap_3
									+ " of the output table 'out7'.");

// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_3 = false;

						tos_count_tMap_3++;

						/**
						 * [tMap_3 main ] stop
						 */

						/**
						 * [tMap_3 process_data_begin ] start
						 */

						currentComponent = "tMap_3";

						/**
						 * [tMap_3 process_data_begin ] stop
						 */
// Start of branch "out7"
						if (out7 != null) {

							/**
							 * [tDBOutput_3 main ] start
							 */

							currentComponent = "tDBOutput_3";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "out7", "tMap_3", "tMap_3", "tMap", "tDBOutput_3", "tDBOutput_3", "tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("out7 - " + (out7 == null ? "" : out7.toLogString()));
							}

							whetherReject_tDBOutput_3 = false;
							if (out7.process_ID == null) {
								pstmt_tDBOutput_3.setNull(1, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(1, out7.process_ID);
							}

							if (out7.job_end_status == null) {
								pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(2, out7.job_end_status);
							}

							if (out7.job_end_time != null) {
								date_tDBOutput_3 = out7.job_end_time.getTime();
								if (date_tDBOutput_3 < year1_tDBOutput_3 || date_tDBOutput_3 >= year10000_tDBOutput_3) {
									pstmt_tDBOutput_3.setString(3, "0000-00-00 00:00:00");
								} else {
									pstmt_tDBOutput_3.setTimestamp(3, new java.sql.Timestamp(date_tDBOutput_3));
								}
							} else {
								pstmt_tDBOutput_3.setNull(3, java.sql.Types.DATE);
							}

							if (out7.error_message == null) {
								pstmt_tDBOutput_3.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(4, out7.error_message);
							}

							if (out7.execution_ID == null) {
								pstmt_tDBOutput_3.setNull(5 + count_tDBOutput_3, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_3.setInt(5 + count_tDBOutput_3, out7.execution_ID);
							}

							pstmt_tDBOutput_3.addBatch();
							nb_line_tDBOutput_3++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_3 - " + ("Adding the record ") + (nb_line_tDBOutput_3)
										+ (" to the ") + ("UPDATE") + (" batch."));
							batchSizeCounter_tDBOutput_3++;
							if (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3) {
								try {
									int countSum_tDBOutput_3 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Executing the ") + ("UPDATE") + (" batch."));
									for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("The ") + ("UPDATE")
												+ (" batch execution has succeeded."));
									updatedCount_tDBOutput_3 += countSum_tDBOutput_3;
									batchSizeCounter_tDBOutput_3 = 0;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									updatedCount_tDBOutput_3 += countSum_tDBOutput_3;
									System.err.println(e.getMessage());
									log.error("tDBOutput_3 - " + (e.getMessage()));
								}
							}

							tos_count_tDBOutput_3++;

							/**
							 * [tDBOutput_3 main ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_3";

							/**
							 * [tDBOutput_3 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_end ] start
							 */

							currentComponent = "tDBOutput_3";

							/**
							 * [tDBOutput_3 process_data_end ] stop
							 */

						} // End of branch "out7"

						/**
						 * [tMap_3 process_data_end ] start
						 */

						currentComponent = "tMap_3";

						/**
						 * [tMap_3 process_data_end ] stop
						 */

						/**
						 * [tLogCatcher_1 process_data_end ] start
						 */

						currentComponent = "tLogCatcher_1";

						/**
						 * [tLogCatcher_1 process_data_end ] stop
						 */

						/**
						 * [tLogCatcher_1 end ] start
						 */

						currentComponent = "tLogCatcher_1";

					}
				} catch (Exception e_tLogCatcher_1) {
					globalMap.put("tLogCatcher_1_ERROR_MESSAGE", e_tLogCatcher_1.getMessage());
					logIgnoredError(String.format(
							"tLogCatcher_1 - tLogCatcher failed to process log message(s) due to internal error: %s",
							e_tLogCatcher_1), e_tLogCatcher_1);
				}

				if (log.isDebugEnabled())
					log.debug("tLogCatcher_1 - " + ("Done."));

				ok_Hash.put("tLogCatcher_1", true);
				end_Hash.put("tLogCatcher_1", System.currentTimeMillis());

				/**
				 * [tLogCatcher_1 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_3 - Written records count in the table 'out7': " + count_out7_tMap_3 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row9", 2, 0,
						"tLogCatcher_1", "tLogCatcher_1", "tLogCatcher", "tMap_3", "tMap_3", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_3 - " + ("Done."));

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (pstmt_tDBOutput_3 != null) {
						int countSum_tDBOutput_3 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("Executing the ") + ("UPDATE") + (" batch."));
						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("The ") + ("UPDATE") + (" batch execution has succeeded."));

						updatedCount_tDBOutput_3 += countSum_tDBOutput_3;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					updatedCount_tDBOutput_3 += countSum_tDBOutput_3;

					log.error("tDBOutput_3 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}

				if (pstmt_tDBOutput_3 != null) {

					pstmt_tDBOutput_3.close();
					resourceMap.remove("pstmt_tDBOutput_3");

				}

				resourceMap.put("statementClosed_tDBOutput_3", true);

				nb_line_deleted_tDBOutput_3 = nb_line_deleted_tDBOutput_3 + deletedCount_tDBOutput_3;
				nb_line_update_tDBOutput_3 = nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
				nb_line_inserted_tDBOutput_3 = nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
				nb_line_rejected_tDBOutput_3 = nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;

				globalMap.put("tDBOutput_3_NB_LINE", nb_line_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_UPDATED", nb_line_update_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_DELETED", nb_line_deleted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out7", 2, 0, "tMap_3",
						"tMap_3", "tMap", "tDBOutput_3", "tDBOutput_3", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Done."));

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tLogCatcher_1 finally ] start
				 */

				currentComponent = "tLogCatcher_1";

				/**
				 * [tLogCatcher_1 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tDBOutput_3 finally ] start
				 */

				currentComponent = "tDBOutput_3";

				if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
					if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_3")) != null) {
						pstmtToClose_tDBOutput_3.close();
					}
				}

				/**
				 * [tDBOutput_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tLogCatcher_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "talendJobLog");
		org.slf4j.MDC.put("_subJobPid", "D0KuHZ_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				ok_Hash.put("talendJobLog", false);
				start_Hash.put("talendJobLog", System.currentTimeMillis());

				currentComponent = "talendJobLog";

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				currentComponent = "talendJobLog";

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				currentComponent = "talendJobLog";

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final product_dimension product_dimensionClass = new product_dimension();

		int exitCode = product_dimensionClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'product_dimension' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20240222_1049-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'product_dimension' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_8GrN4OwPEe6uVJCUD8Lptw");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-03-27T08:38:42.443191Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = product_dimension.class.getClassLoader()
					.getResourceAsStream("finalproject/product_dimension_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = product_dimension.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
					context.setContextType("exeID", "id_Integer");
					if (context.getStringValue("exeID") == null) {
						context.exeID = null;
					} else {
						try {
							context.exeID = routines.system.ParserUtils.parseTo_Integer(context.getProperty("exeID"));
						} catch (NumberFormatException e) {
							log.warn(String.format("Null value will be used for context parameter %s: %s", "exeID",
									e.getMessage()));
							context.exeID = null;
						}
					}
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
			if (parentContextMap.containsKey("exeID")) {
				context.exeID = (Integer) parentContextMap.get("exeID");
			}
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'product_dimension' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		try {
			errorCode = null;
			ExecutionLogStart_1_tPrejob_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_ExecutionLogStart_1_tPrejob_1) {
			globalMap.put("ExecutionLogStart_1_tPrejob_1_SUBPROCESS_STATE", -1);

			e_ExecutionLogStart_1_tPrejob_1.printStackTrace();

		}

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : product_dimension");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'product_dimension' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {
		closeSqlDbConnections();

	}

	private void closeSqlDbConnections() {
		try {
			Object obj_conn;
			obj_conn = globalMap.remove("conn_ExecutionLogStart_1_tDBConnection_1");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
		} catch (java.lang.Exception e) {
		}
	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
		connections.put("conn_ExecutionLogStart_1_tDBConnection_1",
				globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1"));

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 372094 characters generated by Talend Cloud Data Management Platform on the
 * 27 March 2024 at 2:08:42 PM IST
 ************************************************************************************************/