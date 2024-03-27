
package finalproject.stage_0_1;

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
 * Job: stage Purpose: <br>
 * Description: <br>
 * 
 * @author R, Sahitya
 * @version 8.0.1.20240222_1049-patch
 * @status
 */
public class stage implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "stage.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(stage.class);

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

			if (stagepass != null) {

				this.setProperty("stagepass", stagepass.toString());

			}

			if (stagefail != null) {

				this.setProperty("stagefail", stagefail.toString());

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

		public Integer stagepass;

		public Integer getStagepass() {
			return this.stagepass;
		}

		public Integer stagefail;

		public Integer getStagefail() {
			return this.stagefail;
		}
	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "stage";
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
			"_z2UYoN4WEe6TK8DHHwW1PQ", "0.1");
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
					stage.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(stage.this, new Object[] { e, currentComponent, globalMap });
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

	public void tSchemaComplianceCheck_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tConvertType_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

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

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_9_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_7_error(Exception exception, String errorComponent,
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

	public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tLogCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tLogCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tPostjob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tPostjob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBClose_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBClose_1_onSubJobError(exception, errorComponent, globalMap);
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

	public void tDBOutput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_UniqOut_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		tUniqRow_1_UniqIn_error(exception, errorComponent, globalMap);

	}

	public void tUniqRow_1_UniqIn_error(Exception exception, String errorComponent,
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

	public void tLogCatcher_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tPostjob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBClose_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRowGenerator_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_2_onSubJobError(Exception exception, String errorComponent,
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
		org.slf4j.MDC.put("_subJobPid", "ti1pz0_" + subJobPidCounter.getAndIncrement());

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
		org.slf4j.MDC.put("_subJobPid", "DhxWb9_" + subJobPidCounter.getAndIncrement());

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
									"enc:routine.encryption.key.v1:1vp7BBf1gEVCoHWktepCPHTISxpSh9XfnLncJEFUJhza0K4=")
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
								"enc:routine.encryption.key.v1:82hFad++gOqjEqvHUc0/8WiZVRyMB2Q6Ur5UA8COCWlbW+Y=");
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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.var1 = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.ExeID = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		org.slf4j.MDC.put("_subJobPid", "Fqot7C_" + subJobPidCounter.getAndIncrement());

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		org.slf4j.MDC.put("_subJobPid", "WPAo5Z_" + subJobPidCounter.getAndIncrement());

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

	public static class out9Struct implements routines.system.IPersistableRow<out9Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

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

			return null;

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

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		public String errorMessage;

		public String getErrorMessage() {
			return this.errorMessage;
		}

		public Boolean errorMessageIsNullable() {
			return true;
		}

		public Boolean errorMessageIsKey() {
			return false;
		}

		public Integer errorMessageLength() {
			return null;
		}

		public Integer errorMessagePrecision() {
			return null;
		}

		public String errorMessageDefault() {

			return null;

		}

		public String errorMessageComment() {

			return "";

		}

		public String errorMessagePattern() {

			return "";

		}

		public String errorMessageOriginalDbColumnName() {

			return "errorMessage";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.newColumn = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.newColumn = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.newColumn, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.newColumn, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",newColumn=" + newColumn);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append(",errorMessage=" + errorMessage);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (newColumn == null) {
				sb.append("<null>");
			} else {
				sb.append(newColumn);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			if (errorMessage == null) {
				sb.append("<null>");
			} else {
				sb.append(errorMessage);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out9Struct other) {

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

	public static class outStruct implements routines.system.IPersistableRow<outStruct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public java.util.Date Issue_Date;

		public java.util.Date getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "EEEMMMddHH:mm:sszyyyy";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public java.util.Date Crop_Year;

		public java.util.Date getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "yyyy";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public java.util.Date Shipment_Start_Date;

		public java.util.Date getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public java.util.Date Shipment_End_Date;

		public java.util.Date getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		public String Error_Message;

		public String getError_Message() {
			return this.Error_Message;
		}

		public Boolean Error_MessageIsNullable() {
			return true;
		}

		public Boolean Error_MessageIsKey() {
			return false;
		}

		public Integer Error_MessageLength() {
			return null;
		}

		public Integer Error_MessagePrecision() {
			return null;
		}

		public String Error_MessageDefault() {

			return null;

		}

		public String Error_MessageComment() {

			return "";

		}

		public String Error_MessagePattern() {

			return "";

		}

		public String Error_MessageOriginalDbColumnName() {

			return "Error_Message";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.Error_Message = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.Error_Message = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.Error_Message, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.Error_Message, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + String.valueOf(Issue_Date));
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + String.valueOf(Crop_Year));
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + String.valueOf(Shipment_Start_Date));
			sb.append(",Shipment_End_Date=" + String.valueOf(Shipment_End_Date));
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append(",Error_Message=" + Error_Message);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			if (Error_Message == null) {
				sb.append("<null>");
			} else {
				sb.append(Error_Message);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(outStruct other) {

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

	public static class out2Struct implements routines.system.IPersistableRow<out2Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public java.util.Date Issue_Date;

		public java.util.Date getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "EEEMMMddHH:mm:sszyyyy";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public java.util.Date Crop_Year;

		public java.util.Date getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "yyyy";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public java.util.Date Shipment_Start_Date;

		public java.util.Date getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public java.util.Date Shipment_End_Date;

		public java.util.Date getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + String.valueOf(Issue_Date));
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + String.valueOf(Crop_Year));
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + String.valueOf(Shipment_Start_Date));
			sb.append(",Shipment_End_Date=" + String.valueOf(Shipment_End_Date));
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out2Struct other) {

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

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public java.util.Date Issue_Date;

		public java.util.Date getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "EEEMMMddHH:mm:sszyyyy";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public java.util.Date Crop_Year;

		public java.util.Date getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "yyyy";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public java.util.Date Shipment_Start_Date;

		public java.util.Date getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public java.util.Date Shipment_End_Date;

		public java.util.Date getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "dd-MMM-yyyy";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readDate(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readDate(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readDate(dis);

					this.Shipment_End_Date = readDate(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// java.util.Date

				writeDate(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// java.util.Date

				writeDate(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// java.util.Date

				writeDate(this.Shipment_Start_Date, dos);

				// java.util.Date

				writeDate(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + String.valueOf(Issue_Date));
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + String.valueOf(Crop_Year));
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + String.valueOf(Shipment_Start_Date));
			sb.append(",Shipment_End_Date=" + String.valueOf(Shipment_End_Date));
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

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

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public String Issue_Date;

		public String getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + Issue_Date);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
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

	public static class row11Struct implements routines.system.IPersistableRow<row11Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public String Issue_Date;

		public String getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		public String errorCode;

		public String getErrorCode() {
			return this.errorCode;
		}

		public Boolean errorCodeIsNullable() {
			return true;
		}

		public Boolean errorCodeIsKey() {
			return false;
		}

		public Integer errorCodeLength() {
			return 255;
		}

		public Integer errorCodePrecision() {
			return 0;
		}

		public String errorCodeDefault() {

			return null;

		}

		public String errorCodeComment() {

			return null;

		}

		public String errorCodePattern() {

			return null;

		}

		public String errorCodeOriginalDbColumnName() {

			return "errorCode";

		}

		public String errorMessage;

		public String getErrorMessage() {
			return this.errorMessage;
		}

		public Boolean errorMessageIsNullable() {
			return true;
		}

		public Boolean errorMessageIsKey() {
			return false;
		}

		public Integer errorMessageLength() {
			return 255;
		}

		public Integer errorMessagePrecision() {
			return 0;
		}

		public String errorMessageDefault() {

			return null;

		}

		public String errorMessageComment() {

			return null;

		}

		public String errorMessagePattern() {

			return null;

		}

		public String errorMessageOriginalDbColumnName() {

			return "errorMessage";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + Issue_Date);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append(",errorCode=" + errorCode);
			sb.append(",errorMessage=" + errorMessage);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			if (errorCode == null) {
				sb.append("<null>");
			} else {
				sb.append(errorCode);
			}

			sb.append("|");

			if (errorMessage == null) {
				sb.append("<null>");
			} else {
				sb.append(errorMessage);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row11Struct other) {

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

	public static class row10Struct implements routines.system.IPersistableRow<row10Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public String Issue_Date;

		public String getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + Issue_Date);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row10Struct other) {

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public String Issue_Date;

		public String getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + Issue_Date);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String Profit_Center;

		public String getProfit_Center() {
			return this.Profit_Center;
		}

		public Boolean Profit_CenterIsNullable() {
			return true;
		}

		public Boolean Profit_CenterIsKey() {
			return false;
		}

		public Integer Profit_CenterLength() {
			return 300;
		}

		public Integer Profit_CenterPrecision() {
			return 0;
		}

		public String Profit_CenterDefault() {

			return null;

		}

		public String Profit_CenterComment() {

			return "";

		}

		public String Profit_CenterPattern() {

			return "";

		}

		public String Profit_CenterOriginalDbColumnName() {

			return "Profit_Center";

		}

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

		public String Contract_Type;

		public String getContract_Type() {
			return this.Contract_Type;
		}

		public Boolean Contract_TypeIsNullable() {
			return true;
		}

		public Boolean Contract_TypeIsKey() {
			return false;
		}

		public Integer Contract_TypeLength() {
			return 100;
		}

		public Integer Contract_TypePrecision() {
			return 0;
		}

		public String Contract_TypeDefault() {

			return null;

		}

		public String Contract_TypeComment() {

			return "";

		}

		public String Contract_TypePattern() {

			return "";

		}

		public String Contract_TypeOriginalDbColumnName() {

			return "Contract_Type";

		}

		public String Issue_Date;

		public String getIssue_Date() {
			return this.Issue_Date;
		}

		public Boolean Issue_DateIsNullable() {
			return true;
		}

		public Boolean Issue_DateIsKey() {
			return false;
		}

		public Integer Issue_DateLength() {
			return 100;
		}

		public Integer Issue_DatePrecision() {
			return 0;
		}

		public String Issue_DateDefault() {

			return null;

		}

		public String Issue_DateComment() {

			return "";

		}

		public String Issue_DatePattern() {

			return "";

		}

		public String Issue_DateOriginalDbColumnName() {

			return "Issue_Date";

		}

		public String Contract_Status;

		public String getContract_Status() {
			return this.Contract_Status;
		}

		public Boolean Contract_StatusIsNullable() {
			return true;
		}

		public Boolean Contract_StatusIsKey() {
			return false;
		}

		public Integer Contract_StatusLength() {
			return 100;
		}

		public Integer Contract_StatusPrecision() {
			return 0;
		}

		public String Contract_StatusDefault() {

			return null;

		}

		public String Contract_StatusComment() {

			return "";

		}

		public String Contract_StatusPattern() {

			return "";

		}

		public String Contract_StatusOriginalDbColumnName() {

			return "Contract_Status";

		}

		public String Inventory_Status;

		public String getInventory_Status() {
			return this.Inventory_Status;
		}

		public Boolean Inventory_StatusIsNullable() {
			return true;
		}

		public Boolean Inventory_StatusIsKey() {
			return false;
		}

		public Integer Inventory_StatusLength() {
			return 100;
		}

		public Integer Inventory_StatusPrecision() {
			return 0;
		}

		public String Inventory_StatusDefault() {

			return null;

		}

		public String Inventory_StatusComment() {

			return "";

		}

		public String Inventory_StatusPattern() {

			return "";

		}

		public String Inventory_StatusOriginalDbColumnName() {

			return "Inventory_Status";

		}

		public String Contract_Ref__No_;

		public String getContract_Ref__No_() {
			return this.Contract_Ref__No_;
		}

		public Boolean Contract_Ref__No_IsNullable() {
			return true;
		}

		public Boolean Contract_Ref__No_IsKey() {
			return false;
		}

		public Integer Contract_Ref__No_Length() {
			return 5000;
		}

		public Integer Contract_Ref__No_Precision() {
			return 0;
		}

		public String Contract_Ref__No_Default() {

			return null;

		}

		public String Contract_Ref__No_Comment() {

			return "";

		}

		public String Contract_Ref__No_Pattern() {

			return "";

		}

		public String Contract_Ref__No_OriginalDbColumnName() {

			return "Contract_Ref__No_";

		}

		public String CP_Ref_;

		public String getCP_Ref_() {
			return this.CP_Ref_;
		}

		public Boolean CP_Ref_IsNullable() {
			return true;
		}

		public Boolean CP_Ref_IsKey() {
			return false;
		}

		public Integer CP_Ref_Length() {
			return 100;
		}

		public Integer CP_Ref_Precision() {
			return 0;
		}

		public String CP_Ref_Default() {

			return null;

		}

		public String CP_Ref_Comment() {

			return "";

		}

		public String CP_Ref_Pattern() {

			return "";

		}

		public String CP_Ref_OriginalDbColumnName() {

			return "CP_Ref_";

		}

		public String CP_Name;

		public String getCP_Name() {
			return this.CP_Name;
		}

		public Boolean CP_NameIsNullable() {
			return true;
		}

		public Boolean CP_NameIsKey() {
			return false;
		}

		public Integer CP_NameLength() {
			return 100;
		}

		public Integer CP_NamePrecision() {
			return 0;
		}

		public String CP_NameDefault() {

			return null;

		}

		public String CP_NameComment() {

			return "";

		}

		public String CP_NamePattern() {

			return "";

		}

		public String CP_NameOriginalDbColumnName() {

			return "CP_Name";

		}

		public String Allocated_Contract;

		public String getAllocated_Contract() {
			return this.Allocated_Contract;
		}

		public Boolean Allocated_ContractIsNullable() {
			return true;
		}

		public Boolean Allocated_ContractIsKey() {
			return false;
		}

		public Integer Allocated_ContractLength() {
			return 3000;
		}

		public Integer Allocated_ContractPrecision() {
			return 0;
		}

		public String Allocated_ContractDefault() {

			return null;

		}

		public String Allocated_ContractComment() {

			return "";

		}

		public String Allocated_ContractPattern() {

			return "";

		}

		public String Allocated_ContractOriginalDbColumnName() {

			return "Allocated_Contract";

		}

		public String Origin;

		public String getOrigin() {
			return this.Origin;
		}

		public Boolean OriginIsNullable() {
			return true;
		}

		public Boolean OriginIsKey() {
			return false;
		}

		public Integer OriginLength() {
			return 100;
		}

		public Integer OriginPrecision() {
			return 0;
		}

		public String OriginDefault() {

			return null;

		}

		public String OriginComment() {

			return "";

		}

		public String OriginPattern() {

			return "";

		}

		public String OriginOriginalDbColumnName() {

			return "Origin";

		}

		public String Quality;

		public String getQuality() {
			return this.Quality;
		}

		public Boolean QualityIsNullable() {
			return true;
		}

		public Boolean QualityIsKey() {
			return false;
		}

		public Integer QualityLength() {
			return 100;
		}

		public Integer QualityPrecision() {
			return 0;
		}

		public String QualityDefault() {

			return null;

		}

		public String QualityComment() {

			return "";

		}

		public String QualityPattern() {

			return "";

		}

		public String QualityOriginalDbColumnName() {

			return "Quality";

		}

		public String Crop_Year;

		public String getCrop_Year() {
			return this.Crop_Year;
		}

		public Boolean Crop_YearIsNullable() {
			return true;
		}

		public Boolean Crop_YearIsKey() {
			return false;
		}

		public Integer Crop_YearLength() {
			return 100;
		}

		public Integer Crop_YearPrecision() {
			return 0;
		}

		public String Crop_YearDefault() {

			return null;

		}

		public String Crop_YearComment() {

			return "";

		}

		public String Crop_YearPattern() {

			return "";

		}

		public String Crop_YearOriginalDbColumnName() {

			return "Crop_Year";

		}

		public String Quantity;

		public String getQuantity() {
			return this.Quantity;
		}

		public Boolean QuantityIsNullable() {
			return true;
		}

		public Boolean QuantityIsKey() {
			return false;
		}

		public Integer QuantityLength() {
			return 100;
		}

		public Integer QuantityPrecision() {
			return 0;
		}

		public String QuantityDefault() {

			return null;

		}

		public String QuantityComment() {

			return "";

		}

		public String QuantityPattern() {

			return "";

		}

		public String QuantityOriginalDbColumnName() {

			return "Quantity";

		}

		public String Quantity_Unit;

		public String getQuantity_Unit() {
			return this.Quantity_Unit;
		}

		public Boolean Quantity_UnitIsNullable() {
			return true;
		}

		public Boolean Quantity_UnitIsKey() {
			return false;
		}

		public Integer Quantity_UnitLength() {
			return 100;
		}

		public Integer Quantity_UnitPrecision() {
			return 0;
		}

		public String Quantity_UnitDefault() {

			return null;

		}

		public String Quantity_UnitComment() {

			return "";

		}

		public String Quantity_UnitPattern() {

			return "";

		}

		public String Quantity_UnitOriginalDbColumnName() {

			return "Quantity_Unit";

		}

		public String Shipment_Start_Date;

		public String getShipment_Start_Date() {
			return this.Shipment_Start_Date;
		}

		public Boolean Shipment_Start_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_Start_DateIsKey() {
			return false;
		}

		public Integer Shipment_Start_DateLength() {
			return 100;
		}

		public Integer Shipment_Start_DatePrecision() {
			return 0;
		}

		public String Shipment_Start_DateDefault() {

			return null;

		}

		public String Shipment_Start_DateComment() {

			return "";

		}

		public String Shipment_Start_DatePattern() {

			return "";

		}

		public String Shipment_Start_DateOriginalDbColumnName() {

			return "Shipment_Start_Date";

		}

		public String Shipment_End_Date;

		public String getShipment_End_Date() {
			return this.Shipment_End_Date;
		}

		public Boolean Shipment_End_DateIsNullable() {
			return true;
		}

		public Boolean Shipment_End_DateIsKey() {
			return false;
		}

		public Integer Shipment_End_DateLength() {
			return 100;
		}

		public Integer Shipment_End_DatePrecision() {
			return 0;
		}

		public String Shipment_End_DateDefault() {

			return null;

		}

		public String Shipment_End_DateComment() {

			return "";

		}

		public String Shipment_End_DatePattern() {

			return "";

		}

		public String Shipment_End_DateOriginalDbColumnName() {

			return "Shipment_End_Date";

		}

		public String Exchange;

		public String getExchange() {
			return this.Exchange;
		}

		public Boolean ExchangeIsNullable() {
			return true;
		}

		public Boolean ExchangeIsKey() {
			return false;
		}

		public Integer ExchangeLength() {
			return 100;
		}

		public Integer ExchangePrecision() {
			return 0;
		}

		public String ExchangeDefault() {

			return null;

		}

		public String ExchangeComment() {

			return "";

		}

		public String ExchangePattern() {

			return "";

		}

		public String ExchangeOriginalDbColumnName() {

			return "Exchange";

		}

		public String Month;

		public String getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 100;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public String Price;

		public String getPrice() {
			return this.Price;
		}

		public Boolean PriceIsNullable() {
			return true;
		}

		public Boolean PriceIsKey() {
			return false;
		}

		public Integer PriceLength() {
			return 100;
		}

		public Integer PricePrecision() {
			return 0;
		}

		public String PriceDefault() {

			return null;

		}

		public String PriceComment() {

			return "";

		}

		public String PricePattern() {

			return "";

		}

		public String PriceOriginalDbColumnName() {

			return "Price";

		}

		public String Price_units;

		public String getPrice_units() {
			return this.Price_units;
		}

		public Boolean Price_unitsIsNullable() {
			return true;
		}

		public Boolean Price_unitsIsKey() {
			return false;
		}

		public Integer Price_unitsLength() {
			return 100;
		}

		public Integer Price_unitsPrecision() {
			return 0;
		}

		public String Price_unitsDefault() {

			return null;

		}

		public String Price_unitsComment() {

			return "";

		}

		public String Price_unitsPattern() {

			return "";

		}

		public String Price_unitsOriginalDbColumnName() {

			return "Price_units";

		}

		public String INCO_Terms;

		public String getINCO_Terms() {
			return this.INCO_Terms;
		}

		public Boolean INCO_TermsIsNullable() {
			return true;
		}

		public Boolean INCO_TermsIsKey() {
			return false;
		}

		public Integer INCO_TermsLength() {
			return 100;
		}

		public Integer INCO_TermsPrecision() {
			return 0;
		}

		public String INCO_TermsDefault() {

			return null;

		}

		public String INCO_TermsComment() {

			return "";

		}

		public String INCO_TermsPattern() {

			return "";

		}

		public String INCO_TermsOriginalDbColumnName() {

			return "INCO_Terms";

		}

		public String Broker;

		public String getBroker() {
			return this.Broker;
		}

		public Boolean BrokerIsNullable() {
			return true;
		}

		public Boolean BrokerIsKey() {
			return false;
		}

		public Integer BrokerLength() {
			return 100;
		}

		public Integer BrokerPrecision() {
			return 0;
		}

		public String BrokerDefault() {

			return null;

		}

		public String BrokerComment() {

			return "";

		}

		public String BrokerPattern() {

			return "";

		}

		public String BrokerOriginalDbColumnName() {

			return "Broker";

		}

		public String Broker_Ref_No_;

		public String getBroker_Ref_No_() {
			return this.Broker_Ref_No_;
		}

		public Boolean Broker_Ref_No_IsNullable() {
			return true;
		}

		public Boolean Broker_Ref_No_IsKey() {
			return false;
		}

		public Integer Broker_Ref_No_Length() {
			return 100;
		}

		public Integer Broker_Ref_No_Precision() {
			return 0;
		}

		public String Broker_Ref_No_Default() {

			return null;

		}

		public String Broker_Ref_No_Comment() {

			return "";

		}

		public String Broker_Ref_No_Pattern() {

			return "";

		}

		public String Broker_Ref_No_OriginalDbColumnName() {

			return "Broker_Ref_No_";

		}

		public String Commission;

		public String getCommission() {
			return this.Commission;
		}

		public Boolean CommissionIsNullable() {
			return true;
		}

		public Boolean CommissionIsKey() {
			return false;
		}

		public Integer CommissionLength() {
			return 100;
		}

		public Integer CommissionPrecision() {
			return 0;
		}

		public String CommissionDefault() {

			return null;

		}

		public String CommissionComment() {

			return "";

		}

		public String CommissionPattern() {

			return "";

		}

		public String CommissionOriginalDbColumnName() {

			return "Commission";

		}

		public String Sample;

		public String getSample() {
			return this.Sample;
		}

		public Boolean SampleIsNullable() {
			return true;
		}

		public Boolean SampleIsKey() {
			return false;
		}

		public Integer SampleLength() {
			return 5000;
		}

		public Integer SamplePrecision() {
			return 0;
		}

		public String SampleDefault() {

			return null;

		}

		public String SampleComment() {

			return "";

		}

		public String SamplePattern() {

			return "";

		}

		public String SampleOriginalDbColumnName() {

			return "Sample";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.Profit_Center = readString(dis);

					this.Product_Name = readString(dis);

					this.Contract_Type = readString(dis);

					this.Issue_Date = readString(dis);

					this.Contract_Status = readString(dis);

					this.Inventory_Status = readString(dis);

					this.Contract_Ref__No_ = readString(dis);

					this.CP_Ref_ = readString(dis);

					this.CP_Name = readString(dis);

					this.Allocated_Contract = readString(dis);

					this.Origin = readString(dis);

					this.Quality = readString(dis);

					this.Crop_Year = readString(dis);

					this.Quantity = readString(dis);

					this.Quantity_Unit = readString(dis);

					this.Shipment_Start_Date = readString(dis);

					this.Shipment_End_Date = readString(dis);

					this.Exchange = readString(dis);

					this.Month = readString(dis);

					this.Price = readString(dis);

					this.Price_units = readString(dis);

					this.INCO_Terms = readString(dis);

					this.Broker = readString(dis);

					this.Broker_Ref_No_ = readString(dis);

					this.Commission = readString(dis);

					this.Sample = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Profit_Center, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Contract_Type, dos);

				// String

				writeString(this.Issue_Date, dos);

				// String

				writeString(this.Contract_Status, dos);

				// String

				writeString(this.Inventory_Status, dos);

				// String

				writeString(this.Contract_Ref__No_, dos);

				// String

				writeString(this.CP_Ref_, dos);

				// String

				writeString(this.CP_Name, dos);

				// String

				writeString(this.Allocated_Contract, dos);

				// String

				writeString(this.Origin, dos);

				// String

				writeString(this.Quality, dos);

				// String

				writeString(this.Crop_Year, dos);

				// String

				writeString(this.Quantity, dos);

				// String

				writeString(this.Quantity_Unit, dos);

				// String

				writeString(this.Shipment_Start_Date, dos);

				// String

				writeString(this.Shipment_End_Date, dos);

				// String

				writeString(this.Exchange, dos);

				// String

				writeString(this.Month, dos);

				// String

				writeString(this.Price, dos);

				// String

				writeString(this.Price_units, dos);

				// String

				writeString(this.INCO_Terms, dos);

				// String

				writeString(this.Broker, dos);

				// String

				writeString(this.Broker_Ref_No_, dos);

				// String

				writeString(this.Commission, dos);

				// String

				writeString(this.Sample, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Profit_Center=" + Profit_Center);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Contract_Type=" + Contract_Type);
			sb.append(",Issue_Date=" + Issue_Date);
			sb.append(",Contract_Status=" + Contract_Status);
			sb.append(",Inventory_Status=" + Inventory_Status);
			sb.append(",Contract_Ref__No_=" + Contract_Ref__No_);
			sb.append(",CP_Ref_=" + CP_Ref_);
			sb.append(",CP_Name=" + CP_Name);
			sb.append(",Allocated_Contract=" + Allocated_Contract);
			sb.append(",Origin=" + Origin);
			sb.append(",Quality=" + Quality);
			sb.append(",Crop_Year=" + Crop_Year);
			sb.append(",Quantity=" + Quantity);
			sb.append(",Quantity_Unit=" + Quantity_Unit);
			sb.append(",Shipment_Start_Date=" + Shipment_Start_Date);
			sb.append(",Shipment_End_Date=" + Shipment_End_Date);
			sb.append(",Exchange=" + Exchange);
			sb.append(",Month=" + Month);
			sb.append(",Price=" + Price);
			sb.append(",Price_units=" + Price_units);
			sb.append(",INCO_Terms=" + INCO_Terms);
			sb.append(",Broker=" + Broker);
			sb.append(",Broker_Ref_No_=" + Broker_Ref_No_);
			sb.append(",Commission=" + Commission);
			sb.append(",Sample=" + Sample);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Profit_Center == null) {
				sb.append("<null>");
			} else {
				sb.append(Profit_Center);
			}

			sb.append("|");

			if (Product_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_Name);
			}

			sb.append("|");

			if (Contract_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Type);
			}

			sb.append("|");

			if (Issue_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Issue_Date);
			}

			sb.append("|");

			if (Contract_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Status);
			}

			sb.append("|");

			if (Inventory_Status == null) {
				sb.append("<null>");
			} else {
				sb.append(Inventory_Status);
			}

			sb.append("|");

			if (Contract_Ref__No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Contract_Ref__No_);
			}

			sb.append("|");

			if (CP_Ref_ == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Ref_);
			}

			sb.append("|");

			if (CP_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(CP_Name);
			}

			sb.append("|");

			if (Allocated_Contract == null) {
				sb.append("<null>");
			} else {
				sb.append(Allocated_Contract);
			}

			sb.append("|");

			if (Origin == null) {
				sb.append("<null>");
			} else {
				sb.append(Origin);
			}

			sb.append("|");

			if (Quality == null) {
				sb.append("<null>");
			} else {
				sb.append(Quality);
			}

			sb.append("|");

			if (Crop_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Crop_Year);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (Quantity_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity_Unit);
			}

			sb.append("|");

			if (Shipment_Start_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_Start_Date);
			}

			sb.append("|");

			if (Shipment_End_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Shipment_End_Date);
			}

			sb.append("|");

			if (Exchange == null) {
				sb.append("<null>");
			} else {
				sb.append(Exchange);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Price == null) {
				sb.append("<null>");
			} else {
				sb.append(Price);
			}

			sb.append("|");

			if (Price_units == null) {
				sb.append("<null>");
			} else {
				sb.append(Price_units);
			}

			sb.append("|");

			if (INCO_Terms == null) {
				sb.append("<null>");
			} else {
				sb.append(INCO_Terms);
			}

			sb.append("|");

			if (Broker == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker);
			}

			sb.append("|");

			if (Broker_Ref_No_ == null) {
				sb.append("<null>");
			} else {
				sb.append(Broker_Ref_No_);
			}

			sb.append("|");

			if (Commission == null) {
				sb.append("<null>");
			} else {
				sb.append(Commission);
			}

			sb.append("|");

			if (Sample == null) {
				sb.append("<null>");
			} else {
				sb.append(Sample);
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

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBInput_1");
		org.slf4j.MDC.put("_subJobPid", "7jcoeP_" + subJobPidCounter.getAndIncrement());

		String currentVirtualComponent = null;

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

				row1Struct row1 = new row1Struct();
				row10Struct row10 = new row10Struct();
				row3Struct row3 = new row3Struct();
				row5Struct row5 = new row5Struct();
				outStruct out = new outStruct();
				out2Struct out2 = new out2Struct();
				row11Struct row11 = new row11Struct();
				row2Struct row2 = new row2Struct();
				out9Struct out9 = new out9Struct();

				/**
				 * [tUniqRow_1_UniqOut begin ] start
				 */

				ok_Hash.put("tUniqRow_1_UniqOut", false);
				start_Hash.put("tUniqRow_1_UniqOut", System.currentTimeMillis());

				currentVirtualComponent = "tUniqRow_1";

				currentComponent = "tUniqRow_1_UniqOut";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tUniqRow_1_UniqOut = 0;

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1_UniqOut - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tUniqRow_1_UniqOut {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tUniqRow_1_UniqOut = new StringBuilder();
							log4jParamters_tUniqRow_1_UniqOut.append("Parameters:");
							log4jParamters_tUniqRow_1_UniqOut.append("UNIQUE_KEY" + " = " + "[{CASE_SENSITIVE="
									+ ("true") + ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Profit_Center")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("true")
									+ ", SCHEMA_COLUMN=" + ("Product_Name") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Contract_Type")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("true")
									+ ", SCHEMA_COLUMN=" + ("Issue_Date") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Contract_Status")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("true")
									+ ", SCHEMA_COLUMN=" + ("Inventory_Status") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Contract_Ref__No_")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("CP_Ref_") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("CP_Name")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Allocated_Contract") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Origin")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("true")
									+ ", SCHEMA_COLUMN=" + ("Quality") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Crop_Year")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("true")
									+ ", SCHEMA_COLUMN=" + ("Quantity") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("true") + ", SCHEMA_COLUMN=" + ("Quantity_Unit")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Shipment_Start_Date") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Shipment_End_Date")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Exchange") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Month")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Price") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Price_units")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("INCO_Terms") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Broker")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Broker_Ref_No_") + "}, {CASE_SENSITIVE=" + ("true")
									+ ", KEY_ATTRIBUTE=" + ("false") + ", SCHEMA_COLUMN=" + ("Commission")
									+ "}, {CASE_SENSITIVE=" + ("true") + ", KEY_ATTRIBUTE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Sample") + "}]");
							log4jParamters_tUniqRow_1_UniqOut.append(" | ");
							log4jParamters_tUniqRow_1_UniqOut
									.append("TEMP_DIRECTORY" + " = " + "\"/Users/admin/Desktop/RealTimeFiles\"");
							log4jParamters_tUniqRow_1_UniqOut.append(" | ");
							log4jParamters_tUniqRow_1_UniqOut.append("BUFFER_SIZE" + " = " + "M");
							log4jParamters_tUniqRow_1_UniqOut.append(" | ");
							log4jParamters_tUniqRow_1_UniqOut
									.append("IGNORE_TRAILING_ZEROS_FOR_BIGDECIMAL" + " = " + "false");
							log4jParamters_tUniqRow_1_UniqOut.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tUniqRow_1_UniqOut - " + (log4jParamters_tUniqRow_1_UniqOut));
						}
					}
					new BytesLimit65535_tUniqRow_1_UniqOut().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tUniqRow_1_UniqOut", "tUniqRow_1_UniqOut", "tUniqRowOut");
					talendJobLogProcess(globalMap);
				}

//////////////////////////
				long nb_tUniqRow_1 = 0;

				int bufferSize_tUniqRow_1 = 1000000;
				log.debug("tUniqRow_1 - Start to process the data from datasource.");
				class rowStruct_tUniqRow_1 extends row1Struct {

					long id_tUniqRow_1;

					@Override
					public void readData(ObjectInputStream dis) {
						super.readData(dis);
						try {
							this.id_tUniqRow_1 = dis.readLong();
						} catch (IOException e) {
							globalMap.put("tUniqRow_1_ERROR_MESSAGE", e.getMessage());
							throw new RuntimeException(e);
						}
					}

					@Override
					public String toString() {
						return "{" + super.toString() + "\t" + id_tUniqRow_1 + "}";
					}

					@Override
					public void writeData(ObjectOutputStream dos) {
						super.writeData(dos);
						try {
							// Integer
							dos.writeLong(this.id_tUniqRow_1);

						} catch (IOException e) {
							globalMap.put("tUniqRow_1_ERROR_MESSAGE", e.getMessage());
							throw new RuntimeException(e);
						}
					}

					public boolean duplicateTo(rowStruct_tUniqRow_1 other) {

						if (this.Profit_Center == null) {
							if (other.Profit_Center != null) {
								return false;
							}
						} else {
							if (other.Profit_Center == null) {
								return false;
							} else {
								if (!this.Profit_Center.equals(other.Profit_Center)) {
									return false;
								}
							}
						}

						if (this.Product_Name == null) {
							if (other.Product_Name != null) {
								return false;
							}
						} else {
							if (other.Product_Name == null) {
								return false;
							} else {
								if (!this.Product_Name.equals(other.Product_Name)) {
									return false;
								}
							}
						}

						if (this.Contract_Type == null) {
							if (other.Contract_Type != null) {
								return false;
							}
						} else {
							if (other.Contract_Type == null) {
								return false;
							} else {
								if (!this.Contract_Type.equals(other.Contract_Type)) {
									return false;
								}
							}
						}

						if (this.Issue_Date == null) {
							if (other.Issue_Date != null) {
								return false;
							}
						} else {
							if (other.Issue_Date == null) {
								return false;
							} else {
								if (!this.Issue_Date.equals(other.Issue_Date)) {
									return false;
								}
							}
						}

						if (this.Contract_Status == null) {
							if (other.Contract_Status != null) {
								return false;
							}
						} else {
							if (other.Contract_Status == null) {
								return false;
							} else {
								if (!this.Contract_Status.equals(other.Contract_Status)) {
									return false;
								}
							}
						}

						if (this.Inventory_Status == null) {
							if (other.Inventory_Status != null) {
								return false;
							}
						} else {
							if (other.Inventory_Status == null) {
								return false;
							} else {
								if (!this.Inventory_Status.equals(other.Inventory_Status)) {
									return false;
								}
							}
						}

						if (this.Contract_Ref__No_ == null) {
							if (other.Contract_Ref__No_ != null) {
								return false;
							}
						} else {
							if (other.Contract_Ref__No_ == null) {
								return false;
							} else {
								if (!this.Contract_Ref__No_.equals(other.Contract_Ref__No_)) {
									return false;
								}
							}
						}

						if (this.Quality == null) {
							if (other.Quality != null) {
								return false;
							}
						} else {
							if (other.Quality == null) {
								return false;
							} else {
								if (!this.Quality.equals(other.Quality)) {
									return false;
								}
							}
						}

						if (this.Quantity == null) {
							if (other.Quantity != null) {
								return false;
							}
						} else {
							if (other.Quantity == null) {
								return false;
							} else {
								if (!this.Quantity.equals(other.Quantity)) {
									return false;
								}
							}
						}

						if (this.Quantity_Unit == null) {
							if (other.Quantity_Unit != null) {
								return false;
							}
						} else {
							if (other.Quantity_Unit == null) {
								return false;
							} else {
								if (!this.Quantity_Unit.equals(other.Quantity_Unit)) {
									return false;
								}
							}
						}

						return true;
					}

				}

// comparator for first sort
				class Comparator_1_tUniqRow_1 implements Comparator<rowStruct_tUniqRow_1> {

					public int compare(rowStruct_tUniqRow_1 arg0, rowStruct_tUniqRow_1 arg1) {
						int compare = 0;
						if (arg0.Profit_Center == null) {
							if (arg1.Profit_Center != null) {
								return -1;
							}
						} else {
							if (arg1.Profit_Center == null) {
								return 1;
							} else {
								compare = arg0.Profit_Center.compareTo(arg1.Profit_Center);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Product_Name == null) {
							if (arg1.Product_Name != null) {
								return -1;
							}
						} else {
							if (arg1.Product_Name == null) {
								return 1;
							} else {
								compare = arg0.Product_Name.compareTo(arg1.Product_Name);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Contract_Type == null) {
							if (arg1.Contract_Type != null) {
								return -1;
							}
						} else {
							if (arg1.Contract_Type == null) {
								return 1;
							} else {
								compare = arg0.Contract_Type.compareTo(arg1.Contract_Type);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Issue_Date == null) {
							if (arg1.Issue_Date != null) {
								return -1;
							}
						} else {
							if (arg1.Issue_Date == null) {
								return 1;
							} else {
								compare = arg0.Issue_Date.compareTo(arg1.Issue_Date);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Contract_Status == null) {
							if (arg1.Contract_Status != null) {
								return -1;
							}
						} else {
							if (arg1.Contract_Status == null) {
								return 1;
							} else {
								compare = arg0.Contract_Status.compareTo(arg1.Contract_Status);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Inventory_Status == null) {
							if (arg1.Inventory_Status != null) {
								return -1;
							}
						} else {
							if (arg1.Inventory_Status == null) {
								return 1;
							} else {
								compare = arg0.Inventory_Status.compareTo(arg1.Inventory_Status);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Contract_Ref__No_ == null) {
							if (arg1.Contract_Ref__No_ != null) {
								return -1;
							}
						} else {
							if (arg1.Contract_Ref__No_ == null) {
								return 1;
							} else {
								compare = arg0.Contract_Ref__No_.compareTo(arg1.Contract_Ref__No_);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Quality == null) {
							if (arg1.Quality != null) {
								return -1;
							}
						} else {
							if (arg1.Quality == null) {
								return 1;
							} else {
								compare = arg0.Quality.compareTo(arg1.Quality);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Quantity == null) {
							if (arg1.Quantity != null) {
								return -1;
							}
						} else {
							if (arg1.Quantity == null) {
								return 1;
							} else {
								compare = arg0.Quantity.compareTo(arg1.Quantity);
								if (compare != 0) {
									return compare;
								}
							}
						}

						if (arg0.Quantity_Unit == null) {
							if (arg1.Quantity_Unit != null) {
								return -1;
							}
						} else {
							if (arg1.Quantity_Unit == null) {
								return 1;
							} else {
								compare = arg0.Quantity_Unit.compareTo(arg1.Quantity_Unit);
								if (compare != 0) {
									return compare;
								}
							}
						}

						return Long.compare(arg0.id_tUniqRow_1, arg1.id_tUniqRow_1);
					}

				}

				int bufferSize_1_tUniqRow_1 = bufferSize_tUniqRow_1;

				rowStruct_tUniqRow_1[] buffer_1_tUniqRow_1 = new rowStruct_tUniqRow_1[bufferSize_1_tUniqRow_1];

				for (int i_tUniqRow_1 = 0; i_tUniqRow_1 < buffer_1_tUniqRow_1.length; i_tUniqRow_1++) {
					buffer_1_tUniqRow_1[i_tUniqRow_1] = new rowStruct_tUniqRow_1();
				}

				int rowsInBuffer_1_tUniqRow_1 = 0;

				Comparator<rowStruct_tUniqRow_1> comparator_1_tUniqRow_1 = new Comparator_1_tUniqRow_1();

				java.util.ArrayList<java.io.File> files_1_tUniqRow_1 = new java.util.ArrayList<java.io.File>();

				String temp_file_path_prefix_tUniqRow_1 = "/Users/admin/Desktop/RealTimeFiles" + "/" + jobName
						+ "_tUniqRow_1_" + Thread.currentThread().getId() + "_" + pid + "_";

				/**
				 * [tUniqRow_1_UniqOut begin ] stop
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
							log4jParamters_tDBInput_1.append("TABLE" + " = " + "\"Pre_Stage_Table\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERY" + " = " + "\"select * from Pre_Stage_Table\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "true");
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

				String dbquery_tDBInput_1 = "select * from Pre_Stage_Table";

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
							row1.Profit_Center = null;
						} else {

							row1.Profit_Center = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, true);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.Product_Name = null;
						} else {

							row1.Product_Name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, true);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.Contract_Type = null;
						} else {

							row1.Contract_Type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, true);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.Issue_Date = null;
						} else {

							row1.Issue_Date = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, true);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.Contract_Status = null;
						} else {

							row1.Contract_Status = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, true);
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.Inventory_Status = null;
						} else {

							row1.Inventory_Status = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, true);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.Contract_Ref__No_ = null;
						} else {

							row1.Contract_Ref__No_ = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, true);
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.CP_Ref_ = null;
						} else {

							row1.CP_Ref_ = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, true);
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.CP_Name = null;
						} else {

							row1.CP_Name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 9, true);
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.Allocated_Contract = null;
						} else {

							row1.Allocated_Contract = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, true);
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row1.Origin = null;
						} else {

							row1.Origin = routines.system.JDBCUtil.getString(rs_tDBInput_1, 11, true);
						}
						if (colQtyInRs_tDBInput_1 < 12) {
							row1.Quality = null;
						} else {

							row1.Quality = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, true);
						}
						if (colQtyInRs_tDBInput_1 < 13) {
							row1.Crop_Year = null;
						} else {

							row1.Crop_Year = routines.system.JDBCUtil.getString(rs_tDBInput_1, 13, true);
						}
						if (colQtyInRs_tDBInput_1 < 14) {
							row1.Quantity = null;
						} else {

							row1.Quantity = routines.system.JDBCUtil.getString(rs_tDBInput_1, 14, true);
						}
						if (colQtyInRs_tDBInput_1 < 15) {
							row1.Quantity_Unit = null;
						} else {

							row1.Quantity_Unit = routines.system.JDBCUtil.getString(rs_tDBInput_1, 15, true);
						}
						if (colQtyInRs_tDBInput_1 < 16) {
							row1.Shipment_Start_Date = null;
						} else {

							row1.Shipment_Start_Date = routines.system.JDBCUtil.getString(rs_tDBInput_1, 16, true);
						}
						if (colQtyInRs_tDBInput_1 < 17) {
							row1.Shipment_End_Date = null;
						} else {

							row1.Shipment_End_Date = routines.system.JDBCUtil.getString(rs_tDBInput_1, 17, true);
						}
						if (colQtyInRs_tDBInput_1 < 18) {
							row1.Exchange = null;
						} else {

							row1.Exchange = routines.system.JDBCUtil.getString(rs_tDBInput_1, 18, true);
						}
						if (colQtyInRs_tDBInput_1 < 19) {
							row1.Month = null;
						} else {

							row1.Month = routines.system.JDBCUtil.getString(rs_tDBInput_1, 19, true);
						}
						if (colQtyInRs_tDBInput_1 < 20) {
							row1.Price = null;
						} else {

							row1.Price = routines.system.JDBCUtil.getString(rs_tDBInput_1, 20, true);
						}
						if (colQtyInRs_tDBInput_1 < 21) {
							row1.Price_units = null;
						} else {

							row1.Price_units = routines.system.JDBCUtil.getString(rs_tDBInput_1, 21, true);
						}
						if (colQtyInRs_tDBInput_1 < 22) {
							row1.INCO_Terms = null;
						} else {

							row1.INCO_Terms = routines.system.JDBCUtil.getString(rs_tDBInput_1, 22, true);
						}
						if (colQtyInRs_tDBInput_1 < 23) {
							row1.Broker = null;
						} else {

							row1.Broker = routines.system.JDBCUtil.getString(rs_tDBInput_1, 23, true);
						}
						if (colQtyInRs_tDBInput_1 < 24) {
							row1.Broker_Ref_No_ = null;
						} else {

							row1.Broker_Ref_No_ = routines.system.JDBCUtil.getString(rs_tDBInput_1, 24, true);
						}
						if (colQtyInRs_tDBInput_1 < 25) {
							row1.Commission = null;
						} else {

							row1.Commission = routines.system.JDBCUtil.getString(rs_tDBInput_1, 25, true);
						}
						if (colQtyInRs_tDBInput_1 < 26) {
							row1.Sample = null;
						} else {

							row1.Sample = routines.system.JDBCUtil.getString(rs_tDBInput_1, 26, true);
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
						 * [tUniqRow_1_UniqOut main ] start
						 */

						currentVirtualComponent = "tUniqRow_1";

						currentComponent = "tUniqRow_1_UniqOut";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "tDBInput_1", "tMysqlInput", "tUniqRow_1_UniqOut",
								"tUniqRow_1_UniqOut", "tUniqRowOut"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
						}

						if (rowsInBuffer_1_tUniqRow_1 >= bufferSize_1_tUniqRow_1) {// buffer is full do sort and

							java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_1_tUniqRow_1, 0, bufferSize_1_tUniqRow_1,
									comparator_1_tUniqRow_1);

							java.io.File file_tUniqRow_1 = new java.io.File(
									temp_file_path_prefix_tUniqRow_1 + files_1_tUniqRow_1.size());

							log.debug("tUniqRow_1 - Invoke request to delete file: " + file_tUniqRow_1.getPath()
									+ " When VM exit.");

							file_tUniqRow_1.deleteOnExit();
							java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
									new java.io.BufferedOutputStream(new java.io.FileOutputStream(file_tUniqRow_1)));

							log.debug("tUniqRow_1 - Writing the data into: " + file_tUniqRow_1.getPath());

							for (int i_tUniqRow_1 = 0; i_tUniqRow_1 < bufferSize_1_tUniqRow_1; i_tUniqRow_1++) {
								buffer_1_tUniqRow_1[i_tUniqRow_1].writeData(rw);
							}

							rw.close();

							log.debug("tUniqRow_1 - Wrote successfully.");

							files_1_tUniqRow_1.add(file_tUniqRow_1);

							rowsInBuffer_1_tUniqRow_1 = 0;
						}
						rowStruct_tUniqRow_1 row_tUniqRow_1 = buffer_1_tUniqRow_1[rowsInBuffer_1_tUniqRow_1++];
						row_tUniqRow_1.id_tUniqRow_1 = ++nb_tUniqRow_1;
						row_tUniqRow_1.Profit_Center = row1.Profit_Center;

						row_tUniqRow_1.Product_Name = row1.Product_Name;

						row_tUniqRow_1.Contract_Type = row1.Contract_Type;

						row_tUniqRow_1.Issue_Date = row1.Issue_Date;

						row_tUniqRow_1.Contract_Status = row1.Contract_Status;

						row_tUniqRow_1.Inventory_Status = row1.Inventory_Status;

						row_tUniqRow_1.Contract_Ref__No_ = row1.Contract_Ref__No_;

						row_tUniqRow_1.CP_Ref_ = row1.CP_Ref_;

						row_tUniqRow_1.CP_Name = row1.CP_Name;

						row_tUniqRow_1.Allocated_Contract = row1.Allocated_Contract;

						row_tUniqRow_1.Origin = row1.Origin;

						row_tUniqRow_1.Quality = row1.Quality;

						row_tUniqRow_1.Crop_Year = row1.Crop_Year;

						row_tUniqRow_1.Quantity = row1.Quantity;

						row_tUniqRow_1.Quantity_Unit = row1.Quantity_Unit;

						row_tUniqRow_1.Shipment_Start_Date = row1.Shipment_Start_Date;

						row_tUniqRow_1.Shipment_End_Date = row1.Shipment_End_Date;

						row_tUniqRow_1.Exchange = row1.Exchange;

						row_tUniqRow_1.Month = row1.Month;

						row_tUniqRow_1.Price = row1.Price;

						row_tUniqRow_1.Price_units = row1.Price_units;

						row_tUniqRow_1.INCO_Terms = row1.INCO_Terms;

						row_tUniqRow_1.Broker = row1.Broker;

						row_tUniqRow_1.Broker_Ref_No_ = row1.Broker_Ref_No_;

						row_tUniqRow_1.Commission = row1.Commission;

						row_tUniqRow_1.Sample = row1.Sample;

						tos_count_tUniqRow_1_UniqOut++;

						/**
						 * [tUniqRow_1_UniqOut main ] stop
						 */

						/**
						 * [tUniqRow_1_UniqOut process_data_begin ] start
						 */

						currentVirtualComponent = "tUniqRow_1";

						currentComponent = "tUniqRow_1_UniqOut";

						/**
						 * [tUniqRow_1_UniqOut process_data_begin ] stop
						 */

						/**
						 * [tUniqRow_1_UniqOut process_data_end ] start
						 */

						currentVirtualComponent = "tUniqRow_1";

						currentComponent = "tUniqRow_1_UniqOut";

						/**
						 * [tUniqRow_1_UniqOut process_data_end ] stop
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
				 * [tUniqRow_1_UniqOut end ] start
				 */

				currentVirtualComponent = "tUniqRow_1";

				currentComponent = "tUniqRow_1_UniqOut";

				if (rowsInBuffer_1_tUniqRow_1 > 0) {
					java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_1_tUniqRow_1, 0, rowsInBuffer_1_tUniqRow_1,
							comparator_1_tUniqRow_1);

					java.io.File file_tUniqRow_1 = new java.io.File(
							temp_file_path_prefix_tUniqRow_1 + files_1_tUniqRow_1.size());
					log.debug("tUniqRow_1 - Invoke request to delete file: " + file_tUniqRow_1.getPath()
							+ " When VM exit.");

					file_tUniqRow_1.deleteOnExit();
					log.debug("tUniqRow_1 - Writing the data into: " + file_tUniqRow_1.getPath());

					java.io.ObjectOutputStream rw_tUniqRow_1 = new java.io.ObjectOutputStream(
							new java.io.BufferedOutputStream(new java.io.FileOutputStream(file_tUniqRow_1)));
					for (int i = 0; i < rowsInBuffer_1_tUniqRow_1; i++) {
						buffer_1_tUniqRow_1[i].writeData(rw_tUniqRow_1);
					}

					rw_tUniqRow_1.close();

					log.debug("tUniqRow_1 - Wrote successfully.");

					files_1_tUniqRow_1.add(file_tUniqRow_1);

					rowsInBuffer_1_tUniqRow_1 = 0;
				}
				buffer_1_tUniqRow_1 = null;

				// ////////////////////////////////////
				class FileRowIterator_tUniqRow_1 implements java.util.Iterator<rowStruct_tUniqRow_1> {

					boolean isEndOfFile = false;

					rowStruct_tUniqRow_1[] buffer;

					ObjectInputStream ois;

					java.io.BufferedInputStream bis;

					rowStruct_tUniqRow_1 tempRow;

					int count = 0;

					int index = 0;

					public FileRowIterator_tUniqRow_1(java.io.File file, int bufferSize) throws IOException {
						isEndOfFile = false;
						tempRow = null;
						bis = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
						ois = new java.io.ObjectInputStream(bis);
						buffer = new rowStruct_tUniqRow_1[bufferSize];
					}

					private void load() {
						count = 0;
						index = 0;
						try {
							if (tempRow != null) {
								buffer[count++] = tempRow;
								tempRow = null;
							}
							while (!isEndOfFile && count < buffer.length) {
								buffer[count] = new rowStruct_tUniqRow_1();
								buffer[count].readData(ois);
								count++;
							}
							if (!isEndOfFile && count >= buffer.length && bis.available() == 0) {
								tempRow = new rowStruct_tUniqRow_1();
								tempRow.readData(ois);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tUniqRow_1_ERROR_MESSAGE", e.getMessage());
							if (e.getCause() instanceof java.io.EOFException) {
								this.isEndOfFile = true; // the EOFException
								tempRow = null;
							} else {
								throw new RuntimeException(e);
							}
						}
					}

					public boolean hasNext() {
						return index < count || !isEndOfFile;
					}

					public rowStruct_tUniqRow_1 next() {
						if (index >= count) {
							load();
						}

						rowStruct_tUniqRow_1 result = buffer[index];
						// release the reference asap to avoid the memory leak for the worst case
						buffer[index] = null;
						index++;

						return result;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

					public void close() throws IOException {
						// we release the buffer when calling close, but the worst case is that :
						// all the close methods are called one by one at the last, so it's not enough.
						buffer = null;

						if (ois != null) {
							ois.close();
							ois = null;
						}
					}
				}

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "tDBInput_1", "tMysqlInput", "tUniqRow_1_UniqOut", "tUniqRow_1_UniqOut",
						"tUniqRowOut", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1_UniqOut - " + ("Done."));

				ok_Hash.put("tUniqRow_1_UniqOut", true);
				end_Hash.put("tUniqRow_1_UniqOut", System.currentTimeMillis());

				/**
				 * [tUniqRow_1_UniqOut end ] stop
				 */

				/**
				 * [tDBOutput_3 begin ] start
				 */

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out");

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
							log4jParamters_tDBOutput_3.append("TABLE" + " = " + "\"reject_records_stage\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
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

				String tableName_tDBOutput_3 = "reject_records_stage";
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

				int count_tDBOutput_3 = 0;

				String insert_tDBOutput_3 = "INSERT INTO `" + "reject_records_stage"
						+ "` (`Profit_Center`,`Product_Name`,`Contract_Type`,`Issue_Date`,`Contract_Status`,`Inventory_Status`,`Contract_Ref__No_`,`CP_Ref_`,`CP_Name`,`Allocated_Contract`,`Origin`,`Quality`,`Crop_Year`,`Quantity`,`Quantity_Unit`,`Shipment_Start_Date`,`Shipment_End_Date`,`Exchange`,`Month`,`Price`,`Price_units`,`INCO_Terms`,`Broker`,`Broker_Ref_No_`,`Commission`,`Sample`,`Error_Message`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_3 = 100;
				int batchSizeCounter_tDBOutput_3 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out2");

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
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"stage\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "false");
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

				String tableName_tDBOutput_1 = "stage";
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

				String insert_tDBOutput_1 = "INSERT INTO `" + "stage"
						+ "` (`Profit_Center`,`Product_Name`,`Contract_Type`,`Issue_Date`,`Contract_Status`,`Inventory_Status`,`Contract_Ref__No_`,`CP_Ref_`,`CP_Name`,`Allocated_Contract`,`Origin`,`Quality`,`Crop_Year`,`Quantity`,`Quantity_Unit`,`Shipment_Start_Date`,`Shipment_End_Date`,`Exchange`,`Month`,`Price`,`Price_units`,`INCO_Terms`,`Broker`,`Broker_Ref_No_`,`Commission`,`Sample`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_1 = 100;
				int batchSizeCounter_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_6 begin ] start
				 */

				ok_Hash.put("tMap_6", false);
				start_Hash.put("tMap_6", System.currentTimeMillis());

				currentComponent = "tMap_6";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row5");

				int tos_count_tMap_6 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_6 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_6 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_6 = new StringBuilder();
							log4jParamters_tMap_6.append("Parameters:");
							log4jParamters_tMap_6.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_6.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_6 - " + (log4jParamters_tMap_6));
						}
					}
					new BytesLimit65535_tMap_6().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_6", "tMap_6", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row5_tMap_6 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_6__Struct {
				}
				Var__tMap_6__Struct Var__tMap_6 = new Var__tMap_6__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out_tMap_6 = 0;

				outStruct out_tmp = new outStruct();
				int count_out2_tMap_6 = 0;

				out2Struct out2_tmp = new out2Struct();
// ###############################

				/**
				 * [tMap_6 begin ] stop
				 */

				/**
				 * [tConvertType_1 begin ] start
				 */

				ok_Hash.put("tConvertType_1", false);
				start_Hash.put("tConvertType_1", System.currentTimeMillis());

				currentComponent = "tConvertType_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tConvertType_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tConvertType_1", "tConvertType_1", "tConvertType");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tConvertType_1 = 0;

				/**
				 * [tConvertType_1 begin ] stop
				 */

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row11");

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
							log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"reject_records_stage\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
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

				String tableName_tDBOutput_2 = "reject_records_stage";
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

				int count_tDBOutput_2 = 0;

				String insert_tDBOutput_2 = "INSERT INTO `" + "reject_records_stage"
						+ "` (`Profit_Center`,`Product_Name`,`Contract_Type`,`Issue_Date`,`Contract_Status`,`Inventory_Status`,`Contract_Ref__No_`,`CP_Ref_`,`CP_Name`,`Allocated_Contract`,`Origin`,`Quality`,`Crop_Year`,`Quantity`,`Quantity_Unit`,`Shipment_Start_Date`,`Shipment_End_Date`,`Exchange`,`Month`,`Price`,`Price_units`,`INCO_Terms`,`Broker`,`Broker_Ref_No_`,`Commission`,`Sample`,`errorCode`,`errorMessage`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_2 = 100;
				int batchSizeCounter_tDBOutput_2 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 begin ] start
				 */

				ok_Hash.put("tSchemaComplianceCheck_1", false);
				start_Hash.put("tSchemaComplianceCheck_1", System.currentTimeMillis());

				currentComponent = "tSchemaComplianceCheck_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row10");

				int tos_count_tSchemaComplianceCheck_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1",
							"tSchemaComplianceCheck");
					talendJobLogProcess(globalMap);
				}

				class RowSetValueUtil_tSchemaComplianceCheck_1 {

					boolean ifPassedThrough = true;
					int errorCodeThrough = 0;
					String errorMessageThrough = "";
					int resultErrorCodeThrough = 0;
					String resultErrorMessageThrough = "";
					String tmpContentThrough = null;

					boolean ifPassed = true;
					int errorCode = 0;
					String errorMessage = "";

					void handleBigdecimalPrecision(String data, int iPrecision, int maxLength) {
						// number of digits before the decimal point(ignoring frontend zeroes)
						int len1 = 0;
						int len2 = 0;
						ifPassed = true;
						errorCode = 0;
						errorMessage = "";
						if (data.startsWith("-")) {
							data = data.substring(1);
						}
						data = org.apache.commons.lang.StringUtils.stripStart(data, "0");

						if (data.indexOf(".") >= 0) {
							len1 = data.indexOf(".");
							data = org.apache.commons.lang.StringUtils.stripEnd(data, "0");
							len2 = data.length() - (len1 + 1);
						} else {
							len1 = data.length();
						}

						if (iPrecision < len2) {
							ifPassed = false;
							errorCode += 8;
							errorMessage += "|precision Non-matches";
						} else if (maxLength < len1 + iPrecision) {
							ifPassed = false;
							errorCode += 8;
							errorMessage += "|invalid Length setting is unsuitable for Precision";
						}
					}

					int handleErrorCode(int errorCode, int resultErrorCode) {
						if (errorCode > 0) {
							if (resultErrorCode > 0) {
								resultErrorCode = 16;
							} else {
								resultErrorCode = errorCode;
							}
						}
						return resultErrorCode;
					}

					String handleErrorMessage(String errorMessage, String resultErrorMessage, String columnLabel) {
						if (errorMessage.length() > 0) {
							if (resultErrorMessage.length() > 0) {
								resultErrorMessage += ";" + errorMessage.replaceFirst("\\|", columnLabel);
							} else {
								resultErrorMessage = errorMessage.replaceFirst("\\|", columnLabel);
							}
						}
						return resultErrorMessage;
					}

					void reset() {
						ifPassedThrough = true;
						errorCodeThrough = 0;
						errorMessageThrough = "";
						resultErrorCodeThrough = 0;
						resultErrorMessageThrough = "";
						tmpContentThrough = null;

						ifPassed = true;
						errorCode = 0;
						errorMessage = "";
					}

					void setRowValue_0(row10Struct row10) {
						// validate nullable
						if (row10.Profit_Center == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Profit_Center != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Profit_Center);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Profit_Center:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Product_Name == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Product_Name != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Product_Name);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Product_Name:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Contract_Type == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Contract_Type != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Contract_Type);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Contract_Type:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Issue_Date == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Issue_Date != null) {
								if (!TalendDate.isDate((row10.Issue_Date).toString(), "EEEMMMddHH:mm:sszyyyy", false))
									throw new IllegalArgumentException("Data format not matches");
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong DATE pattern or wrong DATE data";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Issue_Date:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Contract_Status == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Contract_Status != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Contract_Status);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Contract_Status:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Inventory_Status == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Inventory_Status != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Inventory_Status);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Inventory_Status:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Contract_Ref__No_ == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Contract_Ref__No_ != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Contract_Ref__No_);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Contract_Ref__No_:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.CP_Ref_ == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.CP_Ref_ != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.CP_Ref_);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"CP_Ref_:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.CP_Name == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.CP_Name != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.CP_Name);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"CP_Name:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Allocated_Contract == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Allocated_Contract != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Allocated_Contract);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Allocated_Contract:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Origin == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Origin != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Origin);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Origin:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Quality == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Quality != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Quality);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Quality:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Crop_Year == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Crop_Year != null) {
								if (!TalendDate.isDate((row10.Crop_Year).toString(), "yyyy", false))
									throw new IllegalArgumentException("Data format not matches");
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong DATE pattern or wrong DATE data";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Crop_Year:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Quantity == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Quantity != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Quantity);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Quantity:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Quantity_Unit == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Quantity_Unit != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Quantity_Unit);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Quantity_Unit:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Shipment_Start_Date == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Shipment_Start_Date != null) {
								if (!TalendDate.isDate((row10.Shipment_Start_Date).toString(), "dd-MMM-yyyy", false))
									throw new IllegalArgumentException("Data format not matches");
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong DATE pattern or wrong DATE data";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Shipment_Start_Date:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Shipment_End_Date == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Shipment_End_Date != null) {
								if (!TalendDate.isDate((row10.Shipment_End_Date).toString(), "dd-MMM-yyyy", false))
									throw new IllegalArgumentException("Data format not matches");
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong DATE pattern or wrong DATE data";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Shipment_End_Date:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Exchange == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Exchange != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Exchange);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Exchange:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Month == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Month != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Month);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Month:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Price == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Price != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Price);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Price:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Price_units == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Price_units != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Price_units);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Price_units:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.INCO_Terms == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.INCO_Terms != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.INCO_Terms);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"INCO_Terms:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Broker == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Broker != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Broker);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Broker:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Broker_Ref_No_ == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Broker_Ref_No_ != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Broker_Ref_No_);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Broker_Ref_No_:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Commission == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Commission != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Commission);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Commission:");
						errorMessageThrough = "";
						// validate nullable
						if (row10.Sample == null) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row10.Sample != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row10.Sample);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"Sample:");
						errorMessageThrough = "";
					}
				}
				RowSetValueUtil_tSchemaComplianceCheck_1 rsvUtil_tSchemaComplianceCheck_1 = new RowSetValueUtil_tSchemaComplianceCheck_1();

				/**
				 * [tSchemaComplianceCheck_1 begin ] stop
				 */

				/**
				 * [tDBOutput_7 begin ] start
				 */

				ok_Hash.put("tDBOutput_7", false);
				start_Hash.put("tDBOutput_7", System.currentTimeMillis());

				currentComponent = "tDBOutput_7";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out9");

				int tos_count_tDBOutput_7 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_7 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_7 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_7 = new StringBuilder();
							log4jParamters_tDBOutput_7.append("Parameters:");
							log4jParamters_tDBOutput_7.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("TABLE" + " = " + "\"reject_records_stage\"");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_7.append(" | ");
							log4jParamters_tDBOutput_7.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_7.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_7 - " + (log4jParamters_tDBOutput_7));
						}
					}
					new BytesLimit65535_tDBOutput_7().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_7", "tDBOutput_7", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_7 = 0;
				int nb_line_update_tDBOutput_7 = 0;
				int nb_line_inserted_tDBOutput_7 = 0;
				int nb_line_deleted_tDBOutput_7 = 0;
				int nb_line_rejected_tDBOutput_7 = 0;

				int deletedCount_tDBOutput_7 = 0;
				int updatedCount_tDBOutput_7 = 0;
				int insertedCount_tDBOutput_7 = 0;
				int rowsToCommitCount_tDBOutput_7 = 0;
				int rejectedCount_tDBOutput_7 = 0;

				String tableName_tDBOutput_7 = "reject_records_stage";
				boolean whetherReject_tDBOutput_7 = false;

				java.util.Calendar calendar_tDBOutput_7 = java.util.Calendar.getInstance();
				calendar_tDBOutput_7.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_7 = calendar_tDBOutput_7.getTime().getTime();
				calendar_tDBOutput_7.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_7 = calendar_tDBOutput_7.getTime().getTime();
				long date_tDBOutput_7;

				java.sql.Connection conn_tDBOutput_7 = null;
				conn_tDBOutput_7 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_7 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_7.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_7.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_7 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_7.getAutoCommit()) + ("'."));

				int count_tDBOutput_7 = 0;

				String insert_tDBOutput_7 = "INSERT INTO `" + "reject_records_stage"
						+ "` (`Profit_Center`,`Product_Name`,`Contract_Type`,`newColumn`,`Contract_Status`,`Inventory_Status`,`Contract_Ref__No_`,`CP_Ref_`,`CP_Name`,`Allocated_Contract`,`Origin`,`Quality`,`Crop_Year`,`Quantity`,`Quantity_Unit`,`Shipment_Start_Date`,`Shipment_End_Date`,`Exchange`,`Month`,`Price`,`Price_units`,`INCO_Terms`,`Broker`,`Broker_Ref_No_`,`Commission`,`Sample`,`errorMessage`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_7 = 100;
				int batchSizeCounter_tDBOutput_7 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_7 = conn_tDBOutput_7.prepareStatement(insert_tDBOutput_7);
				resourceMap.put("pstmt_tDBOutput_7", pstmt_tDBOutput_7);

				/**
				 * [tDBOutput_7 begin ] stop
				 */

				/**
				 * [tMap_9 begin ] start
				 */

				ok_Hash.put("tMap_9", false);
				start_Hash.put("tMap_9", System.currentTimeMillis());

				currentComponent = "tMap_9";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tMap_9 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_9 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_9 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_9 = new StringBuilder();
							log4jParamters_tMap_9.append("Parameters:");
							log4jParamters_tMap_9.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_9.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_9 - " + (log4jParamters_tMap_9));
						}
					}
					new BytesLimit65535_tMap_9().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_9", "tMap_9", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row2_tMap_9 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_9__Struct {
				}
				Var__tMap_9__Struct Var__tMap_9 = new Var__tMap_9__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out9_tMap_9 = 0;

				out9Struct out9_tmp = new out9Struct();
// ###############################

				/**
				 * [tMap_9 begin ] stop
				 */

				/**
				 * [tUniqRow_1_UniqIn begin ] start
				 */

				ok_Hash.put("tUniqRow_1_UniqIn", false);
				start_Hash.put("tUniqRow_1_UniqIn", System.currentTimeMillis());

				currentVirtualComponent = "tUniqRow_1";

				currentComponent = "tUniqRow_1_UniqIn";

				int tos_count_tUniqRow_1_UniqIn = 0;

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1_UniqIn - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tUniqRow_1_UniqIn {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tUniqRow_1_UniqIn = new StringBuilder();
							log4jParamters_tUniqRow_1_UniqIn.append("Parameters:");
							log4jParamters_tUniqRow_1_UniqIn.append("ONLY_ONCE_EACH_DUPLICATED_KEY" + " = " + "false");
							log4jParamters_tUniqRow_1_UniqIn.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tUniqRow_1_UniqIn - " + (log4jParamters_tUniqRow_1_UniqIn));
						}
					}
					new BytesLimit65535_tUniqRow_1_UniqIn().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tUniqRow_1_UniqIn", "tUniqRow_1_UniqIn", "tUniqRowIn");
					talendJobLogProcess(globalMap);
				}

				int bufferSizePerFile_tUniqRow_1 = 10000;

				java.util.List<FileRowIterator_tUniqRow_1> rowFileList_1_tUniqRow_1 = new java.util.ArrayList<FileRowIterator_tUniqRow_1>();
				java.util.List<rowStruct_tUniqRow_1> rowList_1_tUniqRow_1 = new java.util.ArrayList<rowStruct_tUniqRow_1>();

				for (java.io.File file : files_1_tUniqRow_1) {
					FileRowIterator_tUniqRow_1 fri = new FileRowIterator_tUniqRow_1(file, bufferSizePerFile_tUniqRow_1);
					rowFileList_1_tUniqRow_1.add(fri);
					rowList_1_tUniqRow_1.add(fri.next());
				}

				// comparator for second sort
				class Comparator_2_tUniqRow_1 implements Comparator<rowStruct_tUniqRow_1> {

					public int compare(rowStruct_tUniqRow_1 arg0, rowStruct_tUniqRow_1 arg1) {
						return Long.compare(arg0.id_tUniqRow_1, arg1.id_tUniqRow_1);
					}

				}

				// For second sort init begin
				int bufferSize_2_tUniqRow_1 = bufferSize_tUniqRow_1 / 2;
				rowStruct_tUniqRow_1[] buffer_2_tUniqRow_1 = new rowStruct_tUniqRow_1[bufferSize_1_tUniqRow_1];
				int rowsInBuffer_2_tUniqRow_1 = 0;
				Comparator<rowStruct_tUniqRow_1> comparator_2_tUniqRow_1 = new Comparator_2_tUniqRow_1();

				java.util.ArrayList<java.io.File> files_2_tUniqRow_1 = new java.util.ArrayList<java.io.File>();
				// For second sort init end

				// For second sort duplicate init begin
				int bufferSize_3_tUniqRow_1 = bufferSize_tUniqRow_1 / 2;
				rowStruct_tUniqRow_1[] buffer_3_tUniqRow_1 = new rowStruct_tUniqRow_1[bufferSize_3_tUniqRow_1];
				int rowsInBuffer_3_tUniqRow_1 = 0;
				Comparator<rowStruct_tUniqRow_1> comparator_3_tUniqRow_1 = new Comparator_2_tUniqRow_1();

				java.util.ArrayList<java.io.File> files_3_tUniqRow_1 = new java.util.ArrayList<java.io.File>();
				// For second sort duplicate init end

				while (rowList_1_tUniqRow_1.size() > 0) {
					int minIndex_tUniqRow_1 = 0;
					if (rowList_1_tUniqRow_1.size() > 1) {
						for (int i = 1; i < rowList_1_tUniqRow_1.size(); i++) {
							if (comparator_1_tUniqRow_1.compare(rowList_1_tUniqRow_1.get(minIndex_tUniqRow_1),
									rowList_1_tUniqRow_1.get(i)) > 0) {
								minIndex_tUniqRow_1 = i;
							}
						}
					}

					// /////////////
					if (rowsInBuffer_2_tUniqRow_1 >= bufferSize_2_tUniqRow_1) {

						java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_2_tUniqRow_1, 0, bufferSize_2_tUniqRow_1,
								comparator_2_tUniqRow_1);
						java.io.File file = new java.io.File(
								temp_file_path_prefix_tUniqRow_1 + "uniq_" + files_2_tUniqRow_1.size());

						log.debug("tUniqRow_1 - Invoke request to delete file: " + file.getPath() + " When VM exit.");

						file.deleteOnExit();
						java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
								new java.io.BufferedOutputStream(new java.io.FileOutputStream(file)));

						log.debug("tUniqRow_1 - Writing the data into: " + file.getPath());

						for (int i = 0; i < bufferSize_2_tUniqRow_1; i++) {
							buffer_2_tUniqRow_1[i].writeData(rw);
						}
						rw.close();

						log.debug("tUniqRow_1 - Wrote successfully.");

						files_2_tUniqRow_1.add(file);

						rowsInBuffer_2_tUniqRow_1 = 0;
					}
					rowStruct_tUniqRow_1 minItem = rowList_1_tUniqRow_1.get(minIndex_tUniqRow_1);
					buffer_2_tUniqRow_1[rowsInBuffer_2_tUniqRow_1++] = minItem;
					FileRowIterator_tUniqRow_1 fri = rowFileList_1_tUniqRow_1.get(minIndex_tUniqRow_1);
					if (fri.hasNext()) {
						rowList_1_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
					} else {
						fri.close();
						rowFileList_1_tUniqRow_1.remove(minIndex_tUniqRow_1);
						rowList_1_tUniqRow_1.remove(minIndex_tUniqRow_1);
					}

					// get duplicates....begin
					for (int i = 0; i < rowList_1_tUniqRow_1.size();) {
						rowStruct_tUniqRow_1 current = rowList_1_tUniqRow_1.get(i);
						if (current.duplicateTo(minItem)) {
							// current is duplicate....
							if (rowsInBuffer_3_tUniqRow_1 >= bufferSize_3_tUniqRow_1) {

								java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_3_tUniqRow_1, 0,
										bufferSize_3_tUniqRow_1, comparator_3_tUniqRow_1);
								java.io.File file = new java.io.File(
										temp_file_path_prefix_tUniqRow_1 + "duplicate_" + files_3_tUniqRow_1.size());
								log.debug("tUniqRow_1 - Invoke request to delete file: " + file.getPath()
										+ " When VM exit.");

								file.deleteOnExit();
								java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
										new java.io.BufferedOutputStream(new java.io.FileOutputStream(file)));

								log.debug("tUniqRow_1 - Writing the data into: " + file.getPath());

								for (int j = 0; j < bufferSize_3_tUniqRow_1; j++) {
									buffer_3_tUniqRow_1[j].writeData(rw);
								}
								rw.close();

								log.debug("tUniqRow_1 - Wrote successfully.");

								files_3_tUniqRow_1.add(file);

								rowsInBuffer_3_tUniqRow_1 = 0;
							}

							buffer_3_tUniqRow_1[rowsInBuffer_3_tUniqRow_1++] = current;
							rowStruct_tUniqRow_1 noDuplicateItem = null;
							FileRowIterator_tUniqRow_1 fri2 = rowFileList_1_tUniqRow_1.get(i);
							while (fri2.hasNext()) {
								current = fri2.next();
								if (!minItem.duplicateTo(current)) {
									noDuplicateItem = current;
									break;
								} else {
									// current is duplicate....
									if (rowsInBuffer_3_tUniqRow_1 >= bufferSize_3_tUniqRow_1) {

										java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_3_tUniqRow_1, 0,
												bufferSize_3_tUniqRow_1, comparator_3_tUniqRow_1);
										java.io.File file = new java.io.File(temp_file_path_prefix_tUniqRow_1
												+ "duplicate_" + files_3_tUniqRow_1.size());
										log.debug("tUniqRow_1 - Invoke request to delete file: " + file.getPath()
												+ " When VM exit.");

										file.deleteOnExit();
										java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
												new java.io.BufferedOutputStream(new java.io.FileOutputStream(file)));

										log.debug("tUniqRow_1 - Writing the data into: " + file.getPath());

										for (int j = 0; j < bufferSize_3_tUniqRow_1; j++) {
											buffer_3_tUniqRow_1[j].writeData(rw);
										}
										rw.close();

										log.debug("tUniqRow_1 - Wrote successfully.");

										files_3_tUniqRow_1.add(file);

										rowsInBuffer_3_tUniqRow_1 = 0;
									}

									buffer_3_tUniqRow_1[rowsInBuffer_3_tUniqRow_1++] = current;
								}
							}
							if (noDuplicateItem == null) {
								fri2.close();
								rowFileList_1_tUniqRow_1.remove(i);
								rowList_1_tUniqRow_1.remove(i);
							} else {
								rowList_1_tUniqRow_1.set(i, noDuplicateItem);
								i++;
							}
						} else {
							i++;
						}
					}
					// get duplicates....
				}

				if (rowsInBuffer_2_tUniqRow_1 > 0) {

					java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_2_tUniqRow_1, 0, rowsInBuffer_2_tUniqRow_1,
							comparator_2_tUniqRow_1);

					java.io.File file = new java.io.File(
							temp_file_path_prefix_tUniqRow_1 + "uniq_" + files_2_tUniqRow_1.size());

					log.debug("tUniqRow_1 - Invoke request to delete file: " + file.getPath() + " When VM exit.");

					file.deleteOnExit();

					java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
							new java.io.BufferedOutputStream(new java.io.FileOutputStream(file)));

					log.debug("tUniqRow_1 - Writing the data into: " + file.getPath());

					for (int i = 0; i < rowsInBuffer_2_tUniqRow_1; i++) {
						buffer_2_tUniqRow_1[i].writeData(rw);
					}

					rw.close();

					log.debug("tUniqRow_1 - Wrote successfully.");

					files_2_tUniqRow_1.add(file);

					rowsInBuffer_2_tUniqRow_1 = 0;
				}
				buffer_2_tUniqRow_1 = null;

				// current is duplicate....
				if (rowsInBuffer_3_tUniqRow_1 > 0) {

					java.util.Arrays.<rowStruct_tUniqRow_1>sort(buffer_3_tUniqRow_1, 0, rowsInBuffer_3_tUniqRow_1,
							comparator_3_tUniqRow_1);
					java.io.File file = new java.io.File(
							temp_file_path_prefix_tUniqRow_1 + "duplicate_" + files_3_tUniqRow_1.size());
					log.debug("tUniqRow_1 - Invoke request to delete file: " + file.getPath() + " When VM exit.");

					file.deleteOnExit();
					java.io.ObjectOutputStream rw = new java.io.ObjectOutputStream(
							new java.io.BufferedOutputStream(new java.io.FileOutputStream(file)));

					log.debug("tUniqRow_1 - Writing the data into: " + file.getPath());

					for (int j = 0; j < rowsInBuffer_3_tUniqRow_1; j++) {
						buffer_3_tUniqRow_1[j].writeData(rw);
					}
					rw.close();

					log.debug("tUniqRow_1 - Wrote successfully.");

					files_3_tUniqRow_1.add(file);

					rowsInBuffer_3_tUniqRow_1 = 0;
				}
				buffer_3_tUniqRow_1 = null;

				java.util.List<FileRowIterator_tUniqRow_1> rowFileList_2_tUniqRow_1 = new java.util.ArrayList<FileRowIterator_tUniqRow_1>();
				java.util.List<rowStruct_tUniqRow_1> rowList_2_tUniqRow_1 = new java.util.ArrayList<rowStruct_tUniqRow_1>();

				for (java.io.File file : files_2_tUniqRow_1) {
					FileRowIterator_tUniqRow_1 fri = new FileRowIterator_tUniqRow_1(file, bufferSizePerFile_tUniqRow_1);
					rowFileList_2_tUniqRow_1.add(fri);
					rowList_2_tUniqRow_1.add(fri.next());
				}

				java.util.List<FileRowIterator_tUniqRow_1> rowFileList_3_tUniqRow_1 = new java.util.ArrayList<FileRowIterator_tUniqRow_1>();
				java.util.List<rowStruct_tUniqRow_1> rowList_3_tUniqRow_1 = new java.util.ArrayList<rowStruct_tUniqRow_1>();

				for (java.io.File file : files_3_tUniqRow_1) {
					FileRowIterator_tUniqRow_1 fri = new FileRowIterator_tUniqRow_1(file, bufferSizePerFile_tUniqRow_1);
					rowFileList_3_tUniqRow_1.add(fri);
					rowList_3_tUniqRow_1.add(fri.next());
				}

				int nb_uniq_tUniqRow_1 = 0;
				int nb_duplicate_tUniqRow_1 = 0;
				rowStruct_tUniqRow_1 uniq_tUniqRow_1 = null;
				rowStruct_tUniqRow_1 duplicate_tUniqRow_1 = null;
				int minIndex_tUniqRow_1 = 0;
				/////////////////////
				if (rowList_2_tUniqRow_1.size() > 0) {
					minIndex_tUniqRow_1 = 0;
					if (rowList_2_tUniqRow_1.size() > 1) {
						for (int i = 1; i < rowList_2_tUniqRow_1.size(); i++) {
							if (comparator_2_tUniqRow_1.compare(rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1),
									rowList_2_tUniqRow_1.get(i)) > 0) {
								minIndex_tUniqRow_1 = i;
							}
						}
					}
					uniq_tUniqRow_1 = rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
					FileRowIterator_tUniqRow_1 fri = rowFileList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
					if (fri.hasNext()) {
						rowList_2_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
					} else {
						fri.close();
						rowFileList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
						rowList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
					}
				}
				if (rowList_3_tUniqRow_1.size() > 0) {
					minIndex_tUniqRow_1 = 0;
					if (rowList_3_tUniqRow_1.size() > 1) {
						for (int i = 1; i < rowList_3_tUniqRow_1.size(); i++) {
							if (comparator_3_tUniqRow_1.compare(rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1),
									rowList_3_tUniqRow_1.get(i)) > 0) {
								minIndex_tUniqRow_1 = i;
							}
						}
					}
					duplicate_tUniqRow_1 = rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
					FileRowIterator_tUniqRow_1 fri = rowFileList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
					if (fri.hasNext()) {
						rowList_3_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
					} else {
						fri.close();
						rowFileList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
						rowList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
					}
				}
				while (true) {
					row10 = null;
					row2 = null;

					if (uniq_tUniqRow_1 == null) {
						if (duplicate_tUniqRow_1 == null) {
							break;
						} else {
							row2 = new row2Struct();

							log.trace("tUniqRow_1 - Writing the duplicate record " + (nb_duplicate_tUniqRow_1 + 1)
									+ " into row2.");

							row2.Profit_Center = duplicate_tUniqRow_1.Profit_Center;

							row2.Product_Name = duplicate_tUniqRow_1.Product_Name;

							row2.Contract_Type = duplicate_tUniqRow_1.Contract_Type;

							row2.Issue_Date = duplicate_tUniqRow_1.Issue_Date;

							row2.Contract_Status = duplicate_tUniqRow_1.Contract_Status;

							row2.Inventory_Status = duplicate_tUniqRow_1.Inventory_Status;

							row2.Contract_Ref__No_ = duplicate_tUniqRow_1.Contract_Ref__No_;

							row2.CP_Ref_ = duplicate_tUniqRow_1.CP_Ref_;

							row2.CP_Name = duplicate_tUniqRow_1.CP_Name;

							row2.Allocated_Contract = duplicate_tUniqRow_1.Allocated_Contract;

							row2.Origin = duplicate_tUniqRow_1.Origin;

							row2.Quality = duplicate_tUniqRow_1.Quality;

							row2.Crop_Year = duplicate_tUniqRow_1.Crop_Year;

							row2.Quantity = duplicate_tUniqRow_1.Quantity;

							row2.Quantity_Unit = duplicate_tUniqRow_1.Quantity_Unit;

							row2.Shipment_Start_Date = duplicate_tUniqRow_1.Shipment_Start_Date;

							row2.Shipment_End_Date = duplicate_tUniqRow_1.Shipment_End_Date;

							row2.Exchange = duplicate_tUniqRow_1.Exchange;

							row2.Month = duplicate_tUniqRow_1.Month;

							row2.Price = duplicate_tUniqRow_1.Price;

							row2.Price_units = duplicate_tUniqRow_1.Price_units;

							row2.INCO_Terms = duplicate_tUniqRow_1.INCO_Terms;

							row2.Broker = duplicate_tUniqRow_1.Broker;

							row2.Broker_Ref_No_ = duplicate_tUniqRow_1.Broker_Ref_No_;

							row2.Commission = duplicate_tUniqRow_1.Commission;

							row2.Sample = duplicate_tUniqRow_1.Sample;

							nb_duplicate_tUniqRow_1++;
							duplicate_tUniqRow_1 = null;
							if (rowList_3_tUniqRow_1.size() > 0) {
								minIndex_tUniqRow_1 = 0;
								if (rowList_3_tUniqRow_1.size() > 1) {
									for (int i = 1; i < rowList_3_tUniqRow_1.size(); i++) {
										if (comparator_3_tUniqRow_1.compare(
												rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1),
												rowList_3_tUniqRow_1.get(i)) > 0) {
											minIndex_tUniqRow_1 = i;
										}
									}
								}
								duplicate_tUniqRow_1 = rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
								FileRowIterator_tUniqRow_1 fri = rowFileList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
								if (fri.hasNext()) {
									rowList_3_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
								} else {
									fri.close();
									rowFileList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
									rowList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
								}
							}
						}
					} else {
						if (duplicate_tUniqRow_1 == null) {
							row10 = new row10Struct();

							log.trace("tUniqRow_1 - Writing the unique record " + (nb_uniq_tUniqRow_1 + 1)
									+ " into row10.");

							row10.Profit_Center = uniq_tUniqRow_1.Profit_Center;

							row10.Product_Name = uniq_tUniqRow_1.Product_Name;

							row10.Contract_Type = uniq_tUniqRow_1.Contract_Type;

							row10.Issue_Date = uniq_tUniqRow_1.Issue_Date;

							row10.Contract_Status = uniq_tUniqRow_1.Contract_Status;

							row10.Inventory_Status = uniq_tUniqRow_1.Inventory_Status;

							row10.Contract_Ref__No_ = uniq_tUniqRow_1.Contract_Ref__No_;

							row10.CP_Ref_ = uniq_tUniqRow_1.CP_Ref_;

							row10.CP_Name = uniq_tUniqRow_1.CP_Name;

							row10.Allocated_Contract = uniq_tUniqRow_1.Allocated_Contract;

							row10.Origin = uniq_tUniqRow_1.Origin;

							row10.Quality = uniq_tUniqRow_1.Quality;

							row10.Crop_Year = uniq_tUniqRow_1.Crop_Year;

							row10.Quantity = uniq_tUniqRow_1.Quantity;

							row10.Quantity_Unit = uniq_tUniqRow_1.Quantity_Unit;

							row10.Shipment_Start_Date = uniq_tUniqRow_1.Shipment_Start_Date;

							row10.Shipment_End_Date = uniq_tUniqRow_1.Shipment_End_Date;

							row10.Exchange = uniq_tUniqRow_1.Exchange;

							row10.Month = uniq_tUniqRow_1.Month;

							row10.Price = uniq_tUniqRow_1.Price;

							row10.Price_units = uniq_tUniqRow_1.Price_units;

							row10.INCO_Terms = uniq_tUniqRow_1.INCO_Terms;

							row10.Broker = uniq_tUniqRow_1.Broker;

							row10.Broker_Ref_No_ = uniq_tUniqRow_1.Broker_Ref_No_;

							row10.Commission = uniq_tUniqRow_1.Commission;

							row10.Sample = uniq_tUniqRow_1.Sample;

							nb_uniq_tUniqRow_1++;

							uniq_tUniqRow_1 = null;

							if (rowList_2_tUniqRow_1.size() > 0) {
								minIndex_tUniqRow_1 = 0;
								if (rowList_2_tUniqRow_1.size() > 1) {
									for (int i = 1; i < rowList_2_tUniqRow_1.size(); i++) {
										if (comparator_2_tUniqRow_1.compare(
												rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1),
												rowList_2_tUniqRow_1.get(i)) > 0) {
											minIndex_tUniqRow_1 = i;
										}
									}
								}
								uniq_tUniqRow_1 = rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
								FileRowIterator_tUniqRow_1 fri = rowFileList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
								if (fri.hasNext()) {
									rowList_2_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
								} else {
									fri.close();
									rowFileList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
									rowList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
								}
							}

						} else {
							if (uniq_tUniqRow_1.id_tUniqRow_1 < duplicate_tUniqRow_1.id_tUniqRow_1) {
								row10 = new row10Struct();

								log.trace("tUniqRow_1 - Writing the unique record " + (nb_uniq_tUniqRow_1 + 1)
										+ " into row10.");

								row10.Profit_Center = uniq_tUniqRow_1.Profit_Center;

								row10.Product_Name = uniq_tUniqRow_1.Product_Name;

								row10.Contract_Type = uniq_tUniqRow_1.Contract_Type;

								row10.Issue_Date = uniq_tUniqRow_1.Issue_Date;

								row10.Contract_Status = uniq_tUniqRow_1.Contract_Status;

								row10.Inventory_Status = uniq_tUniqRow_1.Inventory_Status;

								row10.Contract_Ref__No_ = uniq_tUniqRow_1.Contract_Ref__No_;

								row10.CP_Ref_ = uniq_tUniqRow_1.CP_Ref_;

								row10.CP_Name = uniq_tUniqRow_1.CP_Name;

								row10.Allocated_Contract = uniq_tUniqRow_1.Allocated_Contract;

								row10.Origin = uniq_tUniqRow_1.Origin;

								row10.Quality = uniq_tUniqRow_1.Quality;

								row10.Crop_Year = uniq_tUniqRow_1.Crop_Year;

								row10.Quantity = uniq_tUniqRow_1.Quantity;

								row10.Quantity_Unit = uniq_tUniqRow_1.Quantity_Unit;

								row10.Shipment_Start_Date = uniq_tUniqRow_1.Shipment_Start_Date;

								row10.Shipment_End_Date = uniq_tUniqRow_1.Shipment_End_Date;

								row10.Exchange = uniq_tUniqRow_1.Exchange;

								row10.Month = uniq_tUniqRow_1.Month;

								row10.Price = uniq_tUniqRow_1.Price;

								row10.Price_units = uniq_tUniqRow_1.Price_units;

								row10.INCO_Terms = uniq_tUniqRow_1.INCO_Terms;

								row10.Broker = uniq_tUniqRow_1.Broker;

								row10.Broker_Ref_No_ = uniq_tUniqRow_1.Broker_Ref_No_;

								row10.Commission = uniq_tUniqRow_1.Commission;

								row10.Sample = uniq_tUniqRow_1.Sample;

								nb_uniq_tUniqRow_1++;

								uniq_tUniqRow_1 = null;

								if (rowList_2_tUniqRow_1.size() > 0) {
									minIndex_tUniqRow_1 = 0;
									if (rowList_2_tUniqRow_1.size() > 1) {
										for (int i = 1; i < rowList_2_tUniqRow_1.size(); i++) {
											if (comparator_2_tUniqRow_1.compare(
													rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1),
													rowList_2_tUniqRow_1.get(i)) > 0) {
												minIndex_tUniqRow_1 = i;
											}
										}
									}
									uniq_tUniqRow_1 = rowList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
									FileRowIterator_tUniqRow_1 fri = rowFileList_2_tUniqRow_1.get(minIndex_tUniqRow_1);
									if (fri.hasNext()) {
										rowList_2_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
									} else {
										fri.close();
										rowFileList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
										rowList_2_tUniqRow_1.remove(minIndex_tUniqRow_1);
									}
								}
							} else {
								row2 = new row2Struct();

								log.trace("tUniqRow_1 - Writing the duplicate record " + (nb_duplicate_tUniqRow_1 + 1)
										+ " into row2.");

								row2.Profit_Center = duplicate_tUniqRow_1.Profit_Center;

								row2.Product_Name = duplicate_tUniqRow_1.Product_Name;

								row2.Contract_Type = duplicate_tUniqRow_1.Contract_Type;

								row2.Issue_Date = duplicate_tUniqRow_1.Issue_Date;

								row2.Contract_Status = duplicate_tUniqRow_1.Contract_Status;

								row2.Inventory_Status = duplicate_tUniqRow_1.Inventory_Status;

								row2.Contract_Ref__No_ = duplicate_tUniqRow_1.Contract_Ref__No_;

								row2.CP_Ref_ = duplicate_tUniqRow_1.CP_Ref_;

								row2.CP_Name = duplicate_tUniqRow_1.CP_Name;

								row2.Allocated_Contract = duplicate_tUniqRow_1.Allocated_Contract;

								row2.Origin = duplicate_tUniqRow_1.Origin;

								row2.Quality = duplicate_tUniqRow_1.Quality;

								row2.Crop_Year = duplicate_tUniqRow_1.Crop_Year;

								row2.Quantity = duplicate_tUniqRow_1.Quantity;

								row2.Quantity_Unit = duplicate_tUniqRow_1.Quantity_Unit;

								row2.Shipment_Start_Date = duplicate_tUniqRow_1.Shipment_Start_Date;

								row2.Shipment_End_Date = duplicate_tUniqRow_1.Shipment_End_Date;

								row2.Exchange = duplicate_tUniqRow_1.Exchange;

								row2.Month = duplicate_tUniqRow_1.Month;

								row2.Price = duplicate_tUniqRow_1.Price;

								row2.Price_units = duplicate_tUniqRow_1.Price_units;

								row2.INCO_Terms = duplicate_tUniqRow_1.INCO_Terms;

								row2.Broker = duplicate_tUniqRow_1.Broker;

								row2.Broker_Ref_No_ = duplicate_tUniqRow_1.Broker_Ref_No_;

								row2.Commission = duplicate_tUniqRow_1.Commission;

								row2.Sample = duplicate_tUniqRow_1.Sample;

								nb_duplicate_tUniqRow_1++;
								duplicate_tUniqRow_1 = null;
								if (rowList_3_tUniqRow_1.size() > 0) {
									minIndex_tUniqRow_1 = 0;
									if (rowList_3_tUniqRow_1.size() > 1) {
										for (int i = 1; i < rowList_3_tUniqRow_1.size(); i++) {
											if (comparator_3_tUniqRow_1.compare(
													rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1),
													rowList_3_tUniqRow_1.get(i)) > 0) {
												minIndex_tUniqRow_1 = i;
											}
										}
									}
									duplicate_tUniqRow_1 = rowList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
									FileRowIterator_tUniqRow_1 fri = rowFileList_3_tUniqRow_1.get(minIndex_tUniqRow_1);
									if (fri.hasNext()) {
										rowList_3_tUniqRow_1.set(minIndex_tUniqRow_1, fri.next());
									} else {
										fri.close();
										rowFileList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
										rowList_3_tUniqRow_1.remove(minIndex_tUniqRow_1);
									}
								}
							}

						}
					}

					/**
					 * [tUniqRow_1_UniqIn begin ] stop
					 */

					/**
					 * [tUniqRow_1_UniqIn main ] start
					 */

					currentVirtualComponent = "tUniqRow_1";

					currentComponent = "tUniqRow_1_UniqIn";

					tos_count_tUniqRow_1_UniqIn++;

					/**
					 * [tUniqRow_1_UniqIn main ] stop
					 */

					/**
					 * [tUniqRow_1_UniqIn process_data_begin ] start
					 */

					currentVirtualComponent = "tUniqRow_1";

					currentComponent = "tUniqRow_1_UniqIn";

					/**
					 * [tUniqRow_1_UniqIn process_data_begin ] stop
					 */
// Start of branch "row10"
					if (row10 != null) {
						row11 = null;

						/**
						 * [tSchemaComplianceCheck_1 main ] start
						 */

						currentComponent = "tSchemaComplianceCheck_1";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row10", "tUniqRow_1_UniqIn", "tUniqRow_1_UniqIn", "tUniqRowIn",
								"tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1", "tSchemaComplianceCheck"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row10 - " + (row10 == null ? "" : row10.toLogString()));
						}

						row3 = null;
						row11 = null;
						rsvUtil_tSchemaComplianceCheck_1.setRowValue_0(row10);
						if (rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
							row3 = new row3Struct();
							row3.Profit_Center = row10.Profit_Center;
							row3.Product_Name = row10.Product_Name;
							row3.Contract_Type = row10.Contract_Type;
							row3.Issue_Date = row10.Issue_Date;
							row3.Contract_Status = row10.Contract_Status;
							row3.Inventory_Status = row10.Inventory_Status;
							row3.Contract_Ref__No_ = row10.Contract_Ref__No_;
							row3.CP_Ref_ = row10.CP_Ref_;
							row3.CP_Name = row10.CP_Name;
							row3.Allocated_Contract = row10.Allocated_Contract;
							row3.Origin = row10.Origin;
							row3.Quality = row10.Quality;
							row3.Crop_Year = row10.Crop_Year;
							row3.Quantity = row10.Quantity;
							row3.Quantity_Unit = row10.Quantity_Unit;
							row3.Shipment_Start_Date = row10.Shipment_Start_Date;
							row3.Shipment_End_Date = row10.Shipment_End_Date;
							row3.Exchange = row10.Exchange;
							row3.Month = row10.Month;
							row3.Price = row10.Price;
							row3.Price_units = row10.Price_units;
							row3.INCO_Terms = row10.INCO_Terms;
							row3.Broker = row10.Broker;
							row3.Broker_Ref_No_ = row10.Broker_Ref_No_;
							row3.Commission = row10.Commission;
							row3.Sample = row10.Sample;
						}
						if (!rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
							row11 = new row11Struct();
							row11.Profit_Center = row10.Profit_Center;
							row11.Product_Name = row10.Product_Name;
							row11.Contract_Type = row10.Contract_Type;
							row11.Issue_Date = row10.Issue_Date;
							row11.Contract_Status = row10.Contract_Status;
							row11.Inventory_Status = row10.Inventory_Status;
							row11.Contract_Ref__No_ = row10.Contract_Ref__No_;
							row11.CP_Ref_ = row10.CP_Ref_;
							row11.CP_Name = row10.CP_Name;
							row11.Allocated_Contract = row10.Allocated_Contract;
							row11.Origin = row10.Origin;
							row11.Quality = row10.Quality;
							row11.Crop_Year = row10.Crop_Year;
							row11.Quantity = row10.Quantity;
							row11.Quantity_Unit = row10.Quantity_Unit;
							row11.Shipment_Start_Date = row10.Shipment_Start_Date;
							row11.Shipment_End_Date = row10.Shipment_End_Date;
							row11.Exchange = row10.Exchange;
							row11.Month = row10.Month;
							row11.Price = row10.Price;
							row11.Price_units = row10.Price_units;
							row11.INCO_Terms = row10.INCO_Terms;
							row11.Broker = row10.Broker;
							row11.Broker_Ref_No_ = row10.Broker_Ref_No_;
							row11.Commission = row10.Commission;
							row11.Sample = row10.Sample;
							row11.errorCode = String.valueOf(rsvUtil_tSchemaComplianceCheck_1.resultErrorCodeThrough);
							row11.errorMessage = rsvUtil_tSchemaComplianceCheck_1.resultErrorMessageThrough;
						}
						rsvUtil_tSchemaComplianceCheck_1.reset();

						tos_count_tSchemaComplianceCheck_1++;

						/**
						 * [tSchemaComplianceCheck_1 main ] stop
						 */

						/**
						 * [tSchemaComplianceCheck_1 process_data_begin ] start
						 */

						currentComponent = "tSchemaComplianceCheck_1";

						/**
						 * [tSchemaComplianceCheck_1 process_data_begin ] stop
						 */
// Start of branch "row3"
						if (row3 != null) {

							/**
							 * [tConvertType_1 main ] start
							 */

							currentComponent = "tConvertType_1";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row3", "tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1",
									"tSchemaComplianceCheck", "tConvertType_1", "tConvertType_1", "tConvertType"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
							}

							row5 = new row5Struct();
							boolean bHasError_tConvertType_1 = false;
							try {
								row5.Profit_Center = TypeConvert.String2String(row3.Profit_Center);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Product_Name = TypeConvert.String2String(row3.Product_Name);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Contract_Type = TypeConvert.String2String(row3.Contract_Type);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Issue_Date = TypeConvert.String2Date(row3.Issue_Date, "EEEMMMddHH:mm:sszyyyy");
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Contract_Status = TypeConvert.String2String(row3.Contract_Status);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Inventory_Status = TypeConvert.String2String(row3.Inventory_Status);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Contract_Ref__No_ = TypeConvert.String2String(row3.Contract_Ref__No_);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.CP_Ref_ = TypeConvert.String2String(row3.CP_Ref_);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.CP_Name = TypeConvert.String2String(row3.CP_Name);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Allocated_Contract = TypeConvert.String2String(row3.Allocated_Contract);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Origin = TypeConvert.String2String(row3.Origin);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Quality = TypeConvert.String2String(row3.Quality);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Crop_Year = TypeConvert.String2Date(row3.Crop_Year, "yyyy");
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Quantity = TypeConvert.String2String(row3.Quantity);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Quantity_Unit = TypeConvert.String2String(row3.Quantity_Unit);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Shipment_Start_Date = TypeConvert.String2Date(row3.Shipment_Start_Date,
										"dd-MMM-yyyy");
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Shipment_End_Date = TypeConvert.String2Date(row3.Shipment_End_Date, "dd-MMM-yyyy");
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Exchange = TypeConvert.String2String(row3.Exchange);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Month = TypeConvert.String2String(row3.Month);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Price = TypeConvert.String2String(row3.Price);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Price_units = TypeConvert.String2String(row3.Price_units);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.INCO_Terms = TypeConvert.String2String(row3.INCO_Terms);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Broker = TypeConvert.String2String(row3.Broker);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Broker_Ref_No_ = TypeConvert.String2String(row3.Broker_Ref_No_);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Commission = TypeConvert.String2String(row3.Commission);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							try {
								row5.Sample = TypeConvert.String2String(row3.Sample);
							} catch (java.lang.Exception e) {
								globalMap.put("tConvertType_1_ERROR_MESSAGE", e.getMessage());
								bHasError_tConvertType_1 = true;
								System.err.println(e.getMessage());
							}
							if (bHasError_tConvertType_1) {
								row5 = null;
							}

							nb_line_tConvertType_1++;

							tos_count_tConvertType_1++;

							/**
							 * [tConvertType_1 main ] stop
							 */

							/**
							 * [tConvertType_1 process_data_begin ] start
							 */

							currentComponent = "tConvertType_1";

							/**
							 * [tConvertType_1 process_data_begin ] stop
							 */
// Start of branch "row5"
							if (row5 != null) {

								/**
								 * [tMap_6 main ] start
								 */

								currentComponent = "tMap_6";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row5", "tConvertType_1", "tConvertType_1", "tConvertType", "tMap_6",
										"tMap_6", "tMap"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row5 - " + (row5 == null ? "" : row5.toLogString()));
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_6 = false;

								// ###############################
								// # Input tables (lookups)

								boolean rejectedInnerJoin_tMap_6 = false;
								boolean mainRowRejected_tMap_6 = false;
								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_6__Struct Var = Var__tMap_6;// ###############################
									// ###############################
									// # Output tables

									out = null;
									out2 = null;

// # Output table : 'out'
// # Filter conditions 
									if (

									TalendDate.compareDate(row5.Shipment_Start_Date, row5.Shipment_End_Date) > 0

									) {
										count_out_tMap_6++;

										out_tmp.Profit_Center = row5.Profit_Center;
										out_tmp.Product_Name = row5.Product_Name;
										out_tmp.Contract_Type = row5.Contract_Type;
										out_tmp.Issue_Date = row5.Issue_Date;
										out_tmp.Contract_Status = row5.Contract_Status;
										out_tmp.Inventory_Status = row5.Inventory_Status;
										out_tmp.Contract_Ref__No_ = row5.Contract_Ref__No_;
										out_tmp.CP_Ref_ = row5.CP_Ref_;
										out_tmp.CP_Name = row5.CP_Name;
										out_tmp.Allocated_Contract = row5.Allocated_Contract;
										out_tmp.Origin = row5.Origin;
										out_tmp.Quality = row5.Quality;
										out_tmp.Crop_Year = row5.Crop_Year;
										out_tmp.Quantity = row5.Quantity;
										out_tmp.Quantity_Unit = row5.Quantity_Unit;
										out_tmp.Shipment_Start_Date = row5.Shipment_Start_Date;
										out_tmp.Shipment_End_Date = row5.Shipment_End_Date;
										out_tmp.Exchange = row5.Exchange;
										out_tmp.Month = row5.Month;
										out_tmp.Price = row5.Price;
										out_tmp.Price_units = row5.Price_units;
										out_tmp.INCO_Terms = row5.INCO_Terms;
										out_tmp.Broker = row5.Broker;
										out_tmp.Broker_Ref_No_ = row5.Broker_Ref_No_;
										out_tmp.Commission = row5.Commission;
										out_tmp.Sample = row5.Sample;
										out_tmp.Error_Message = "Improper shipment date";
										out = out_tmp;
										log.debug("tMap_6 - Outputting the record " + count_out_tMap_6
												+ " of the output table 'out'.");

									} // closing filter/reject

// # Output table : 'out2'
// # Filter conditions 
									if (

									TalendDate.compareDate(row5.Shipment_Start_Date, row5.Shipment_End_Date) <= 0

									) {
										count_out2_tMap_6++;

										out2_tmp.Profit_Center = row5.Profit_Center;
										out2_tmp.Product_Name = row5.Product_Name;
										out2_tmp.Contract_Type = row5.Contract_Type;
										out2_tmp.Issue_Date = row5.Issue_Date;
										out2_tmp.Contract_Status = row5.Contract_Status;
										out2_tmp.Inventory_Status = row5.Inventory_Status;
										out2_tmp.Contract_Ref__No_ = row5.Contract_Ref__No_;
										out2_tmp.CP_Ref_ = row5.CP_Ref_;
										out2_tmp.CP_Name = row5.CP_Name;
										out2_tmp.Allocated_Contract = row5.Allocated_Contract;
										out2_tmp.Origin = row5.Origin;
										out2_tmp.Quality = row5.Quality;
										out2_tmp.Crop_Year = row5.Crop_Year;
										out2_tmp.Quantity = row5.Quantity;
										out2_tmp.Quantity_Unit = row5.Quantity_Unit;
										out2_tmp.Shipment_Start_Date = row5.Shipment_Start_Date;
										out2_tmp.Shipment_End_Date = row5.Shipment_End_Date;
										out2_tmp.Exchange = row5.Exchange;
										out2_tmp.Month = row5.Month;
										out2_tmp.Price = row5.Price;
										out2_tmp.Price_units = row5.Price_units;
										out2_tmp.INCO_Terms = row5.INCO_Terms;
										out2_tmp.Broker = row5.Broker;
										out2_tmp.Broker_Ref_No_ = row5.Broker_Ref_No_;
										out2_tmp.Commission = row5.Commission;
										out2_tmp.Sample = row5.Sample;
										out2 = out2_tmp;
										log.debug("tMap_6 - Outputting the record " + count_out2_tMap_6
												+ " of the output table 'out2'.");

									} // closing filter/reject
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_6 = false;

								tos_count_tMap_6++;

								/**
								 * [tMap_6 main ] stop
								 */

								/**
								 * [tMap_6 process_data_begin ] start
								 */

								currentComponent = "tMap_6";

								/**
								 * [tMap_6 process_data_begin ] stop
								 */
// Start of branch "out"
								if (out != null) {

									/**
									 * [tDBOutput_3 main ] start
									 */

									currentComponent = "tDBOutput_3";

									if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

											, "out", "tMap_6", "tMap_6", "tMap", "tDBOutput_3", "tDBOutput_3",
											"tMysqlOutput"

									)) {
										talendJobLogProcess(globalMap);
									}

									if (log.isTraceEnabled()) {
										log.trace("out - " + (out == null ? "" : out.toLogString()));
									}

									whetherReject_tDBOutput_3 = false;
									if (out.Profit_Center == null) {
										pstmt_tDBOutput_3.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(1, out.Profit_Center);
									}

									if (out.Product_Name == null) {
										pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(2, out.Product_Name);
									}

									if (out.Contract_Type == null) {
										pstmt_tDBOutput_3.setNull(3, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(3, out.Contract_Type);
									}

									if (out.Issue_Date != null) {
										date_tDBOutput_3 = out.Issue_Date.getTime();
										if (date_tDBOutput_3 < year1_tDBOutput_3
												|| date_tDBOutput_3 >= year10000_tDBOutput_3) {
											pstmt_tDBOutput_3.setString(4, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_3.setTimestamp(4, new java.sql.Timestamp(date_tDBOutput_3));
										}
									} else {
										pstmt_tDBOutput_3.setNull(4, java.sql.Types.DATE);
									}

									if (out.Contract_Status == null) {
										pstmt_tDBOutput_3.setNull(5, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(5, out.Contract_Status);
									}

									if (out.Inventory_Status == null) {
										pstmt_tDBOutput_3.setNull(6, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(6, out.Inventory_Status);
									}

									if (out.Contract_Ref__No_ == null) {
										pstmt_tDBOutput_3.setNull(7, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(7, out.Contract_Ref__No_);
									}

									if (out.CP_Ref_ == null) {
										pstmt_tDBOutput_3.setNull(8, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(8, out.CP_Ref_);
									}

									if (out.CP_Name == null) {
										pstmt_tDBOutput_3.setNull(9, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(9, out.CP_Name);
									}

									if (out.Allocated_Contract == null) {
										pstmt_tDBOutput_3.setNull(10, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(10, out.Allocated_Contract);
									}

									if (out.Origin == null) {
										pstmt_tDBOutput_3.setNull(11, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(11, out.Origin);
									}

									if (out.Quality == null) {
										pstmt_tDBOutput_3.setNull(12, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(12, out.Quality);
									}

									if (out.Crop_Year != null) {
										date_tDBOutput_3 = out.Crop_Year.getTime();
										if (date_tDBOutput_3 < year1_tDBOutput_3
												|| date_tDBOutput_3 >= year10000_tDBOutput_3) {
											pstmt_tDBOutput_3.setString(13, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_3.setTimestamp(13,
													new java.sql.Timestamp(date_tDBOutput_3));
										}
									} else {
										pstmt_tDBOutput_3.setNull(13, java.sql.Types.DATE);
									}

									if (out.Quantity == null) {
										pstmt_tDBOutput_3.setNull(14, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(14, out.Quantity);
									}

									if (out.Quantity_Unit == null) {
										pstmt_tDBOutput_3.setNull(15, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(15, out.Quantity_Unit);
									}

									if (out.Shipment_Start_Date != null) {
										date_tDBOutput_3 = out.Shipment_Start_Date.getTime();
										if (date_tDBOutput_3 < year1_tDBOutput_3
												|| date_tDBOutput_3 >= year10000_tDBOutput_3) {
											pstmt_tDBOutput_3.setString(16, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_3.setTimestamp(16,
													new java.sql.Timestamp(date_tDBOutput_3));
										}
									} else {
										pstmt_tDBOutput_3.setNull(16, java.sql.Types.DATE);
									}

									if (out.Shipment_End_Date != null) {
										date_tDBOutput_3 = out.Shipment_End_Date.getTime();
										if (date_tDBOutput_3 < year1_tDBOutput_3
												|| date_tDBOutput_3 >= year10000_tDBOutput_3) {
											pstmt_tDBOutput_3.setString(17, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_3.setTimestamp(17,
													new java.sql.Timestamp(date_tDBOutput_3));
										}
									} else {
										pstmt_tDBOutput_3.setNull(17, java.sql.Types.DATE);
									}

									if (out.Exchange == null) {
										pstmt_tDBOutput_3.setNull(18, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(18, out.Exchange);
									}

									if (out.Month == null) {
										pstmt_tDBOutput_3.setNull(19, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(19, out.Month);
									}

									if (out.Price == null) {
										pstmt_tDBOutput_3.setNull(20, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(20, out.Price);
									}

									if (out.Price_units == null) {
										pstmt_tDBOutput_3.setNull(21, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(21, out.Price_units);
									}

									if (out.INCO_Terms == null) {
										pstmt_tDBOutput_3.setNull(22, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(22, out.INCO_Terms);
									}

									if (out.Broker == null) {
										pstmt_tDBOutput_3.setNull(23, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(23, out.Broker);
									}

									if (out.Broker_Ref_No_ == null) {
										pstmt_tDBOutput_3.setNull(24, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(24, out.Broker_Ref_No_);
									}

									if (out.Commission == null) {
										pstmt_tDBOutput_3.setNull(25, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(25, out.Commission);
									}

									if (out.Sample == null) {
										pstmt_tDBOutput_3.setNull(26, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(26, out.Sample);
									}

									if (out.Error_Message == null) {
										pstmt_tDBOutput_3.setNull(27, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(27, out.Error_Message);
									}

									pstmt_tDBOutput_3.addBatch();
									nb_line_tDBOutput_3++;

									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Adding the record ") + (nb_line_tDBOutput_3)
												+ (" to the ") + ("INSERT") + (" batch."));
									batchSizeCounter_tDBOutput_3++;
									if (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3) {
										try {
											int countSum_tDBOutput_3 = 0;
											if (log.isDebugEnabled())
												log.debug("tDBOutput_3 - " + ("Executing the ") + ("INSERT")
														+ (" batch."));
											for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
												countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED
														? 0
														: 1);
											}
											rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
											if (log.isDebugEnabled())
												log.debug("tDBOutput_3 - " + ("The ") + ("INSERT")
														+ (" batch execution has succeeded."));
											insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
										} catch (java.sql.BatchUpdateException e) {
											globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
											int countSum_tDBOutput_3 = 0;
											for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
												countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0
														: countEach_tDBOutput_3);
											}
											rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
											insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
											System.err.println(e.getMessage());
											log.error("tDBOutput_3 - " + (e.getMessage()));
										}

										batchSizeCounter_tDBOutput_3 = 0;
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

								} // End of branch "out"

// Start of branch "out2"
								if (out2 != null) {

									/**
									 * [tDBOutput_1 main ] start
									 */

									currentComponent = "tDBOutput_1";

									if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

											, "out2", "tMap_6", "tMap_6", "tMap", "tDBOutput_1", "tDBOutput_1",
											"tMysqlOutput"

									)) {
										talendJobLogProcess(globalMap);
									}

									if (log.isTraceEnabled()) {
										log.trace("out2 - " + (out2 == null ? "" : out2.toLogString()));
									}

									whetherReject_tDBOutput_1 = false;
									if (out2.Profit_Center == null) {
										pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(1, out2.Profit_Center);
									}

									if (out2.Product_Name == null) {
										pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(2, out2.Product_Name);
									}

									if (out2.Contract_Type == null) {
										pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(3, out2.Contract_Type);
									}

									if (out2.Issue_Date != null) {
										date_tDBOutput_1 = out2.Issue_Date.getTime();
										if (date_tDBOutput_1 < year1_tDBOutput_1
												|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
											pstmt_tDBOutput_1.setString(4, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_1.setTimestamp(4, new java.sql.Timestamp(date_tDBOutput_1));
										}
									} else {
										pstmt_tDBOutput_1.setNull(4, java.sql.Types.DATE);
									}

									if (out2.Contract_Status == null) {
										pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(5, out2.Contract_Status);
									}

									if (out2.Inventory_Status == null) {
										pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(6, out2.Inventory_Status);
									}

									if (out2.Contract_Ref__No_ == null) {
										pstmt_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(7, out2.Contract_Ref__No_);
									}

									if (out2.CP_Ref_ == null) {
										pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(8, out2.CP_Ref_);
									}

									if (out2.CP_Name == null) {
										pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(9, out2.CP_Name);
									}

									if (out2.Allocated_Contract == null) {
										pstmt_tDBOutput_1.setNull(10, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(10, out2.Allocated_Contract);
									}

									if (out2.Origin == null) {
										pstmt_tDBOutput_1.setNull(11, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(11, out2.Origin);
									}

									if (out2.Quality == null) {
										pstmt_tDBOutput_1.setNull(12, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(12, out2.Quality);
									}

									if (out2.Crop_Year != null) {
										date_tDBOutput_1 = out2.Crop_Year.getTime();
										if (date_tDBOutput_1 < year1_tDBOutput_1
												|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
											pstmt_tDBOutput_1.setString(13, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_1.setTimestamp(13,
													new java.sql.Timestamp(date_tDBOutput_1));
										}
									} else {
										pstmt_tDBOutput_1.setNull(13, java.sql.Types.DATE);
									}

									if (out2.Quantity == null) {
										pstmt_tDBOutput_1.setNull(14, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(14, out2.Quantity);
									}

									if (out2.Quantity_Unit == null) {
										pstmt_tDBOutput_1.setNull(15, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(15, out2.Quantity_Unit);
									}

									if (out2.Shipment_Start_Date != null) {
										date_tDBOutput_1 = out2.Shipment_Start_Date.getTime();
										if (date_tDBOutput_1 < year1_tDBOutput_1
												|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
											pstmt_tDBOutput_1.setString(16, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_1.setTimestamp(16,
													new java.sql.Timestamp(date_tDBOutput_1));
										}
									} else {
										pstmt_tDBOutput_1.setNull(16, java.sql.Types.DATE);
									}

									if (out2.Shipment_End_Date != null) {
										date_tDBOutput_1 = out2.Shipment_End_Date.getTime();
										if (date_tDBOutput_1 < year1_tDBOutput_1
												|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
											pstmt_tDBOutput_1.setString(17, "0000-00-00 00:00:00");
										} else {
											pstmt_tDBOutput_1.setTimestamp(17,
													new java.sql.Timestamp(date_tDBOutput_1));
										}
									} else {
										pstmt_tDBOutput_1.setNull(17, java.sql.Types.DATE);
									}

									if (out2.Exchange == null) {
										pstmt_tDBOutput_1.setNull(18, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(18, out2.Exchange);
									}

									if (out2.Month == null) {
										pstmt_tDBOutput_1.setNull(19, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(19, out2.Month);
									}

									if (out2.Price == null) {
										pstmt_tDBOutput_1.setNull(20, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(20, out2.Price);
									}

									if (out2.Price_units == null) {
										pstmt_tDBOutput_1.setNull(21, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(21, out2.Price_units);
									}

									if (out2.INCO_Terms == null) {
										pstmt_tDBOutput_1.setNull(22, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(22, out2.INCO_Terms);
									}

									if (out2.Broker == null) {
										pstmt_tDBOutput_1.setNull(23, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(23, out2.Broker);
									}

									if (out2.Broker_Ref_No_ == null) {
										pstmt_tDBOutput_1.setNull(24, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(24, out2.Broker_Ref_No_);
									}

									if (out2.Commission == null) {
										pstmt_tDBOutput_1.setNull(25, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(25, out2.Commission);
									}

									if (out2.Sample == null) {
										pstmt_tDBOutput_1.setNull(26, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(26, out2.Sample);
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
												log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT")
														+ (" batch."));
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
											int countSum_tDBOutput_1 = 0;
											for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
												countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
														: countEach_tDBOutput_1);
											}
											rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
											insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
											System.err.println(e.getMessage());
											log.error("tDBOutput_1 - " + (e.getMessage()));
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

								} // End of branch "out2"

								/**
								 * [tMap_6 process_data_end ] start
								 */

								currentComponent = "tMap_6";

								/**
								 * [tMap_6 process_data_end ] stop
								 */

							} // End of branch "row5"

							/**
							 * [tConvertType_1 process_data_end ] start
							 */

							currentComponent = "tConvertType_1";

							/**
							 * [tConvertType_1 process_data_end ] stop
							 */

						} // End of branch "row3"

// Start of branch "row11"
						if (row11 != null) {

							/**
							 * [tDBOutput_2 main ] start
							 */

							currentComponent = "tDBOutput_2";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row11", "tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1",
									"tSchemaComplianceCheck", "tDBOutput_2", "tDBOutput_2", "tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row11 - " + (row11 == null ? "" : row11.toLogString()));
							}

							whetherReject_tDBOutput_2 = false;
							if (row11.Profit_Center == null) {
								pstmt_tDBOutput_2.setNull(1, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(1, row11.Profit_Center);
							}

							if (row11.Product_Name == null) {
								pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(2, row11.Product_Name);
							}

							if (row11.Contract_Type == null) {
								pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(3, row11.Contract_Type);
							}

							if (row11.Issue_Date == null) {
								pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(4, row11.Issue_Date);
							}

							if (row11.Contract_Status == null) {
								pstmt_tDBOutput_2.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(5, row11.Contract_Status);
							}

							if (row11.Inventory_Status == null) {
								pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(6, row11.Inventory_Status);
							}

							if (row11.Contract_Ref__No_ == null) {
								pstmt_tDBOutput_2.setNull(7, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(7, row11.Contract_Ref__No_);
							}

							if (row11.CP_Ref_ == null) {
								pstmt_tDBOutput_2.setNull(8, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(8, row11.CP_Ref_);
							}

							if (row11.CP_Name == null) {
								pstmt_tDBOutput_2.setNull(9, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(9, row11.CP_Name);
							}

							if (row11.Allocated_Contract == null) {
								pstmt_tDBOutput_2.setNull(10, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(10, row11.Allocated_Contract);
							}

							if (row11.Origin == null) {
								pstmt_tDBOutput_2.setNull(11, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(11, row11.Origin);
							}

							if (row11.Quality == null) {
								pstmt_tDBOutput_2.setNull(12, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(12, row11.Quality);
							}

							if (row11.Crop_Year == null) {
								pstmt_tDBOutput_2.setNull(13, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(13, row11.Crop_Year);
							}

							if (row11.Quantity == null) {
								pstmt_tDBOutput_2.setNull(14, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(14, row11.Quantity);
							}

							if (row11.Quantity_Unit == null) {
								pstmt_tDBOutput_2.setNull(15, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(15, row11.Quantity_Unit);
							}

							if (row11.Shipment_Start_Date == null) {
								pstmt_tDBOutput_2.setNull(16, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(16, row11.Shipment_Start_Date);
							}

							if (row11.Shipment_End_Date == null) {
								pstmt_tDBOutput_2.setNull(17, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(17, row11.Shipment_End_Date);
							}

							if (row11.Exchange == null) {
								pstmt_tDBOutput_2.setNull(18, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(18, row11.Exchange);
							}

							if (row11.Month == null) {
								pstmt_tDBOutput_2.setNull(19, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(19, row11.Month);
							}

							if (row11.Price == null) {
								pstmt_tDBOutput_2.setNull(20, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(20, row11.Price);
							}

							if (row11.Price_units == null) {
								pstmt_tDBOutput_2.setNull(21, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(21, row11.Price_units);
							}

							if (row11.INCO_Terms == null) {
								pstmt_tDBOutput_2.setNull(22, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(22, row11.INCO_Terms);
							}

							if (row11.Broker == null) {
								pstmt_tDBOutput_2.setNull(23, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(23, row11.Broker);
							}

							if (row11.Broker_Ref_No_ == null) {
								pstmt_tDBOutput_2.setNull(24, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(24, row11.Broker_Ref_No_);
							}

							if (row11.Commission == null) {
								pstmt_tDBOutput_2.setNull(25, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(25, row11.Commission);
							}

							if (row11.Sample == null) {
								pstmt_tDBOutput_2.setNull(26, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(26, row11.Sample);
							}

							if (row11.errorCode == null) {
								pstmt_tDBOutput_2.setNull(27, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(27, row11.errorCode);
							}

							if (row11.errorMessage == null) {
								pstmt_tDBOutput_2.setNull(28, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(28, row11.errorMessage);
							}

							pstmt_tDBOutput_2.addBatch();
							nb_line_tDBOutput_2++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_2 - " + ("Adding the record ") + (nb_line_tDBOutput_2)
										+ (" to the ") + ("INSERT") + (" batch."));
							batchSizeCounter_tDBOutput_2++;
							if (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2) {
								try {
									int countSum_tDBOutput_2 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
									System.err.println(e.getMessage());
									log.error("tDBOutput_2 - " + (e.getMessage()));
								}

								batchSizeCounter_tDBOutput_2 = 0;
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

						} // End of branch "row11"

						/**
						 * [tSchemaComplianceCheck_1 process_data_end ] start
						 */

						currentComponent = "tSchemaComplianceCheck_1";

						/**
						 * [tSchemaComplianceCheck_1 process_data_end ] stop
						 */

					} // End of branch "row10"

// Start of branch "row2"
					if (row2 != null) {

						/**
						 * [tMap_9 main ] start
						 */

						currentComponent = "tMap_9";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row2", "tUniqRow_1_UniqIn", "tUniqRow_1_UniqIn", "tUniqRowIn", "tMap_9", "tMap_9",
								"tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_9 = false;

						// ###############################
						// # Input tables (lookups)

						boolean rejectedInnerJoin_tMap_9 = false;
						boolean mainRowRejected_tMap_9 = false;
						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_9__Struct Var = Var__tMap_9;// ###############################
							// ###############################
							// # Output tables

							out9 = null;

// # Output table : 'out9'
							count_out9_tMap_9++;

							out9_tmp.Profit_Center = row2.Profit_Center;
							out9_tmp.Product_Name = row2.Product_Name;
							out9_tmp.Contract_Type = row2.Contract_Type;
							out9_tmp.newColumn = row2.Issue_Date;
							out9_tmp.Contract_Status = row2.Contract_Status;
							out9_tmp.Inventory_Status = row2.Inventory_Status;
							out9_tmp.Contract_Ref__No_ = row2.Contract_Ref__No_;
							out9_tmp.CP_Ref_ = row2.CP_Ref_;
							out9_tmp.CP_Name = row2.CP_Name;
							out9_tmp.Allocated_Contract = row2.Allocated_Contract;
							out9_tmp.Origin = row2.Origin;
							out9_tmp.Quality = row2.Quality;
							out9_tmp.Crop_Year = row2.Crop_Year;
							out9_tmp.Quantity = row2.Quantity;
							out9_tmp.Quantity_Unit = row2.Quantity_Unit;
							out9_tmp.Shipment_Start_Date = row2.Shipment_Start_Date;
							out9_tmp.Shipment_End_Date = row2.Shipment_End_Date;
							out9_tmp.Exchange = row2.Exchange;
							out9_tmp.Month = row2.Month;
							out9_tmp.Price = row2.Price;
							out9_tmp.Price_units = row2.Price_units;
							out9_tmp.INCO_Terms = row2.INCO_Terms;
							out9_tmp.Broker = row2.Broker;
							out9_tmp.Broker_Ref_No_ = row2.Broker_Ref_No_;
							out9_tmp.Commission = row2.Commission;
							out9_tmp.Sample = row2.Sample;
							out9_tmp.errorMessage = "Duplicate Records";
							out9 = out9_tmp;
							log.debug("tMap_9 - Outputting the record " + count_out9_tMap_9
									+ " of the output table 'out9'.");

// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_9 = false;

						tos_count_tMap_9++;

						/**
						 * [tMap_9 main ] stop
						 */

						/**
						 * [tMap_9 process_data_begin ] start
						 */

						currentComponent = "tMap_9";

						/**
						 * [tMap_9 process_data_begin ] stop
						 */
// Start of branch "out9"
						if (out9 != null) {

							/**
							 * [tDBOutput_7 main ] start
							 */

							currentComponent = "tDBOutput_7";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "out9", "tMap_9", "tMap_9", "tMap", "tDBOutput_7", "tDBOutput_7", "tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("out9 - " + (out9 == null ? "" : out9.toLogString()));
							}

							whetherReject_tDBOutput_7 = false;
							if (out9.Profit_Center == null) {
								pstmt_tDBOutput_7.setNull(1, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(1, out9.Profit_Center);
							}

							if (out9.Product_Name == null) {
								pstmt_tDBOutput_7.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(2, out9.Product_Name);
							}

							if (out9.Contract_Type == null) {
								pstmt_tDBOutput_7.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(3, out9.Contract_Type);
							}

							if (out9.newColumn == null) {
								pstmt_tDBOutput_7.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(4, out9.newColumn);
							}

							if (out9.Contract_Status == null) {
								pstmt_tDBOutput_7.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(5, out9.Contract_Status);
							}

							if (out9.Inventory_Status == null) {
								pstmt_tDBOutput_7.setNull(6, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(6, out9.Inventory_Status);
							}

							if (out9.Contract_Ref__No_ == null) {
								pstmt_tDBOutput_7.setNull(7, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(7, out9.Contract_Ref__No_);
							}

							if (out9.CP_Ref_ == null) {
								pstmt_tDBOutput_7.setNull(8, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(8, out9.CP_Ref_);
							}

							if (out9.CP_Name == null) {
								pstmt_tDBOutput_7.setNull(9, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(9, out9.CP_Name);
							}

							if (out9.Allocated_Contract == null) {
								pstmt_tDBOutput_7.setNull(10, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(10, out9.Allocated_Contract);
							}

							if (out9.Origin == null) {
								pstmt_tDBOutput_7.setNull(11, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(11, out9.Origin);
							}

							if (out9.Quality == null) {
								pstmt_tDBOutput_7.setNull(12, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(12, out9.Quality);
							}

							if (out9.Crop_Year == null) {
								pstmt_tDBOutput_7.setNull(13, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(13, out9.Crop_Year);
							}

							if (out9.Quantity == null) {
								pstmt_tDBOutput_7.setNull(14, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(14, out9.Quantity);
							}

							if (out9.Quantity_Unit == null) {
								pstmt_tDBOutput_7.setNull(15, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(15, out9.Quantity_Unit);
							}

							if (out9.Shipment_Start_Date == null) {
								pstmt_tDBOutput_7.setNull(16, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(16, out9.Shipment_Start_Date);
							}

							if (out9.Shipment_End_Date == null) {
								pstmt_tDBOutput_7.setNull(17, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(17, out9.Shipment_End_Date);
							}

							if (out9.Exchange == null) {
								pstmt_tDBOutput_7.setNull(18, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(18, out9.Exchange);
							}

							if (out9.Month == null) {
								pstmt_tDBOutput_7.setNull(19, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(19, out9.Month);
							}

							if (out9.Price == null) {
								pstmt_tDBOutput_7.setNull(20, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(20, out9.Price);
							}

							if (out9.Price_units == null) {
								pstmt_tDBOutput_7.setNull(21, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(21, out9.Price_units);
							}

							if (out9.INCO_Terms == null) {
								pstmt_tDBOutput_7.setNull(22, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(22, out9.INCO_Terms);
							}

							if (out9.Broker == null) {
								pstmt_tDBOutput_7.setNull(23, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(23, out9.Broker);
							}

							if (out9.Broker_Ref_No_ == null) {
								pstmt_tDBOutput_7.setNull(24, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(24, out9.Broker_Ref_No_);
							}

							if (out9.Commission == null) {
								pstmt_tDBOutput_7.setNull(25, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(25, out9.Commission);
							}

							if (out9.Sample == null) {
								pstmt_tDBOutput_7.setNull(26, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(26, out9.Sample);
							}

							if (out9.errorMessage == null) {
								pstmt_tDBOutput_7.setNull(27, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_7.setString(27, out9.errorMessage);
							}

							pstmt_tDBOutput_7.addBatch();
							nb_line_tDBOutput_7++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_7 - " + ("Adding the record ") + (nb_line_tDBOutput_7)
										+ (" to the ") + ("INSERT") + (" batch."));
							batchSizeCounter_tDBOutput_7++;
							if (batchSize_tDBOutput_7 <= batchSizeCounter_tDBOutput_7) {
								try {
									int countSum_tDBOutput_7 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_7 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_7 : pstmt_tDBOutput_7.executeBatch()) {
										countSum_tDBOutput_7 += (countEach_tDBOutput_7 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_7 += countSum_tDBOutput_7;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_7 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_7 += countSum_tDBOutput_7;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_7_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_7 = 0;
									for (int countEach_tDBOutput_7 : e.getUpdateCounts()) {
										countSum_tDBOutput_7 += (countEach_tDBOutput_7 < 0 ? 0 : countEach_tDBOutput_7);
									}
									rowsToCommitCount_tDBOutput_7 += countSum_tDBOutput_7;
									insertedCount_tDBOutput_7 += countSum_tDBOutput_7;
									System.err.println(e.getMessage());
									log.error("tDBOutput_7 - " + (e.getMessage()));
								}

								batchSizeCounter_tDBOutput_7 = 0;
							}

							tos_count_tDBOutput_7++;

							/**
							 * [tDBOutput_7 main ] stop
							 */

							/**
							 * [tDBOutput_7 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_7";

							/**
							 * [tDBOutput_7 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_7 process_data_end ] start
							 */

							currentComponent = "tDBOutput_7";

							/**
							 * [tDBOutput_7 process_data_end ] stop
							 */

						} // End of branch "out9"

						/**
						 * [tMap_9 process_data_end ] start
						 */

						currentComponent = "tMap_9";

						/**
						 * [tMap_9 process_data_end ] stop
						 */

					} // End of branch "row2"

					/**
					 * [tUniqRow_1_UniqIn process_data_end ] start
					 */

					currentVirtualComponent = "tUniqRow_1";

					currentComponent = "tUniqRow_1_UniqIn";

					/**
					 * [tUniqRow_1_UniqIn process_data_end ] stop
					 */

					/**
					 * [tUniqRow_1_UniqIn end ] start
					 */

					currentVirtualComponent = "tUniqRow_1";

					currentComponent = "tUniqRow_1_UniqIn";

				}
				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniq_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicate_tUniqRow_1);
				log.info("tUniqRow_1 - Unique records count: " + (nb_uniq_tUniqRow_1) + " .");
				log.info("tUniqRow_1 - Duplicate records count: " + (nb_duplicate_tUniqRow_1) + " .");

				if (log.isDebugEnabled())
					log.debug("tUniqRow_1_UniqIn - " + ("Done."));

				ok_Hash.put("tUniqRow_1_UniqIn", true);
				end_Hash.put("tUniqRow_1_UniqIn", System.currentTimeMillis());

				/**
				 * [tUniqRow_1_UniqIn end ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 end ] start
				 */

				currentComponent = "tSchemaComplianceCheck_1";

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row10", 2, 0,
						"tUniqRow_1_UniqIn", "tUniqRow_1_UniqIn", "tUniqRowIn", "tSchemaComplianceCheck_1",
						"tSchemaComplianceCheck_1", "tSchemaComplianceCheck", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tSchemaComplianceCheck_1", true);
				end_Hash.put("tSchemaComplianceCheck_1", System.currentTimeMillis());

				/**
				 * [tSchemaComplianceCheck_1 end ] stop
				 */

				/**
				 * [tConvertType_1 end ] start
				 */

				currentComponent = "tConvertType_1";

				globalMap.put("tConvertType_1_NB_LINE", nb_line_tConvertType_1);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1", "tSchemaComplianceCheck",
						"tConvertType_1", "tConvertType_1", "tConvertType", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tConvertType_1", true);
				end_Hash.put("tConvertType_1", System.currentTimeMillis());

				/**
				 * [tConvertType_1 end ] stop
				 */

				/**
				 * [tMap_6 end ] start
				 */

				currentComponent = "tMap_6";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_6 - Written records count in the table 'out': " + count_out_tMap_6 + ".");
				log.debug("tMap_6 - Written records count in the table 'out2': " + count_out2_tMap_6 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row5", 2, 0,
						"tConvertType_1", "tConvertType_1", "tConvertType", "tMap_6", "tMap_6", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_6 - " + ("Done."));

				ok_Hash.put("tMap_6", true);
				end_Hash.put("tMap_6", System.currentTimeMillis());

				/**
				 * [tMap_6 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (batchSizeCounter_tDBOutput_3 != 0) {
						int countSum_tDBOutput_3 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					log.error("tDBOutput_3 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_3 = 0;

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

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out", 2, 0, "tMap_6",
						"tMap_6", "tMap", "tDBOutput_3", "tDBOutput_3", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Done."));

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
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

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					log.error("tDBOutput_1 - " + (e.getMessage()));
					System.err.println(e.getMessage());

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

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out2", 2, 0, "tMap_6",
						"tMap_6", "tMap", "tDBOutput_1", "tDBOutput_1", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Done."));

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (batchSizeCounter_tDBOutput_2 != 0) {
						int countSum_tDBOutput_2 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					log.error("tDBOutput_2 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_2 = 0;

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

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row11", 2, 0,
						"tSchemaComplianceCheck_1", "tSchemaComplianceCheck_1", "tSchemaComplianceCheck", "tDBOutput_2",
						"tDBOutput_2", "tMysqlOutput", "reject")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Done."));

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

				/**
				 * [tMap_9 end ] start
				 */

				currentComponent = "tMap_9";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_9 - Written records count in the table 'out9': " + count_out9_tMap_9 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tUniqRow_1_UniqIn", "tUniqRow_1_UniqIn", "tUniqRowIn", "tMap_9", "tMap_9", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_9 - " + ("Done."));

				ok_Hash.put("tMap_9", true);
				end_Hash.put("tMap_9", System.currentTimeMillis());

				/**
				 * [tMap_9 end ] stop
				 */

				/**
				 * [tDBOutput_7 end ] start
				 */

				currentComponent = "tDBOutput_7";

				try {
					if (batchSizeCounter_tDBOutput_7 != 0) {
						int countSum_tDBOutput_7 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_7 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_7 : pstmt_tDBOutput_7.executeBatch()) {
							countSum_tDBOutput_7 += (countEach_tDBOutput_7 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_7 += countSum_tDBOutput_7;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_7 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_7 += countSum_tDBOutput_7;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_7 = 0;
					for (int countEach_tDBOutput_7 : e.getUpdateCounts()) {
						countSum_tDBOutput_7 += (countEach_tDBOutput_7 < 0 ? 0 : countEach_tDBOutput_7);
					}
					rowsToCommitCount_tDBOutput_7 += countSum_tDBOutput_7;

					insertedCount_tDBOutput_7 += countSum_tDBOutput_7;

					log.error("tDBOutput_7 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_7 = 0;

				if (pstmt_tDBOutput_7 != null) {

					pstmt_tDBOutput_7.close();
					resourceMap.remove("pstmt_tDBOutput_7");

				}

				resourceMap.put("statementClosed_tDBOutput_7", true);

				nb_line_deleted_tDBOutput_7 = nb_line_deleted_tDBOutput_7 + deletedCount_tDBOutput_7;
				nb_line_update_tDBOutput_7 = nb_line_update_tDBOutput_7 + updatedCount_tDBOutput_7;
				nb_line_inserted_tDBOutput_7 = nb_line_inserted_tDBOutput_7 + insertedCount_tDBOutput_7;
				nb_line_rejected_tDBOutput_7 = nb_line_rejected_tDBOutput_7 + rejectedCount_tDBOutput_7;

				globalMap.put("tDBOutput_7_NB_LINE", nb_line_tDBOutput_7);
				globalMap.put("tDBOutput_7_NB_LINE_UPDATED", nb_line_update_tDBOutput_7);
				globalMap.put("tDBOutput_7_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_7);
				globalMap.put("tDBOutput_7_NB_LINE_DELETED", nb_line_deleted_tDBOutput_7);
				globalMap.put("tDBOutput_7_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_7);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out9", 2, 0, "tMap_9",
						"tMap_9", "tMap", "tDBOutput_7", "tDBOutput_7", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_7 - " + ("Done."));

				ok_Hash.put("tDBOutput_7", true);
				end_Hash.put("tDBOutput_7", System.currentTimeMillis());

				/**
				 * [tDBOutput_7 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1_UniqOut finally ] start
				 */

				currentVirtualComponent = "tUniqRow_1";

				currentComponent = "tUniqRow_1_UniqOut";

				/**
				 * [tUniqRow_1_UniqOut finally ] stop
				 */

				/**
				 * [tUniqRow_1_UniqIn finally ] start
				 */

				currentVirtualComponent = "tUniqRow_1";

				currentComponent = "tUniqRow_1_UniqIn";

				/**
				 * [tUniqRow_1_UniqIn finally ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 finally ] start
				 */

				currentComponent = "tSchemaComplianceCheck_1";

				/**
				 * [tSchemaComplianceCheck_1 finally ] stop
				 */

				/**
				 * [tConvertType_1 finally ] start
				 */

				currentComponent = "tConvertType_1";

				/**
				 * [tConvertType_1 finally ] stop
				 */

				/**
				 * [tMap_6 finally ] start
				 */

				currentComponent = "tMap_6";

				/**
				 * [tMap_6 finally ] stop
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

				/**
				 * [tMap_9 finally ] start
				 */

				currentComponent = "tMap_9";

				/**
				 * [tMap_9 finally ] stop
				 */

				/**
				 * [tDBOutput_7 finally ] start
				 */

				currentComponent = "tDBOutput_7";

				if (resourceMap.get("statementClosed_tDBOutput_7") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_7 = null;
					if ((pstmtToClose_tDBOutput_7 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_7")) != null) {
						pstmtToClose_tDBOutput_7.close();
					}
				}

				/**
				 * [tDBOutput_7 finally ] stop
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

	public static class out7Struct implements routines.system.IPersistableRow<out7Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		org.slf4j.MDC.put("_subJobPid", "VgYR58_" + subJobPidCounter.getAndIncrement());

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
				 * [tDBOutput_6 begin ] start
				 */

				ok_Hash.put("tDBOutput_6", false);
				start_Hash.put("tDBOutput_6", System.currentTimeMillis());

				currentComponent = "tDBOutput_6";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out7");

				int tos_count_tDBOutput_6 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_6 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_6 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_6 = new StringBuilder();
							log4jParamters_tDBOutput_6.append("Parameters:");
							log4jParamters_tDBOutput_6.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("TABLE" + " = " + "\"Execution_Log_Table\"");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("DATA_ACTION" + " = " + "UPDATE");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_6.append(" | ");
							log4jParamters_tDBOutput_6.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_6.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_6 - " + (log4jParamters_tDBOutput_6));
						}
					}
					new BytesLimit65535_tDBOutput_6().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_6", "tDBOutput_6", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int updateKeyCount_tDBOutput_6 = 1;
				if (updateKeyCount_tDBOutput_6 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_6 == 5 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
				}

				int nb_line_tDBOutput_6 = 0;
				int nb_line_update_tDBOutput_6 = 0;
				int nb_line_inserted_tDBOutput_6 = 0;
				int nb_line_deleted_tDBOutput_6 = 0;
				int nb_line_rejected_tDBOutput_6 = 0;

				int deletedCount_tDBOutput_6 = 0;
				int updatedCount_tDBOutput_6 = 0;
				int insertedCount_tDBOutput_6 = 0;
				int rowsToCommitCount_tDBOutput_6 = 0;
				int rejectedCount_tDBOutput_6 = 0;

				String tableName_tDBOutput_6 = "Execution_Log_Table";
				boolean whetherReject_tDBOutput_6 = false;

				java.util.Calendar calendar_tDBOutput_6 = java.util.Calendar.getInstance();
				calendar_tDBOutput_6.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_6 = calendar_tDBOutput_6.getTime().getTime();
				calendar_tDBOutput_6.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_6 = calendar_tDBOutput_6.getTime().getTime();
				long date_tDBOutput_6;

				java.sql.Connection conn_tDBOutput_6 = null;
				conn_tDBOutput_6 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_6 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_6.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_6.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_6 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_6.getAutoCommit()) + ("'."));
				int batchSize_tDBOutput_6 = 10000;
				int batchSizeCounter_tDBOutput_6 = 0;

				int count_tDBOutput_6 = 0;

				String update_tDBOutput_6 = "UPDATE `" + "Execution_Log_Table"
						+ "` SET `process_ID` = ?,`job_end_status` = ?,`job_end_time` = ?,`error_message` = ? WHERE `execution_ID` = ?";

				java.sql.PreparedStatement pstmt_tDBOutput_6 = conn_tDBOutput_6.prepareStatement(update_tDBOutput_6);
				resourceMap.put("pstmt_tDBOutput_6", pstmt_tDBOutput_6);

				/**
				 * [tDBOutput_6 begin ] stop
				 */

				/**
				 * [tMap_5 begin ] start
				 */

				ok_Hash.put("tMap_5", false);
				start_Hash.put("tMap_5", System.currentTimeMillis());

				currentComponent = "tMap_5";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row9");

				int tos_count_tMap_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_5 = new StringBuilder();
							log4jParamters_tMap_5.append("Parameters:");
							log4jParamters_tMap_5.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_5 - " + (log4jParamters_tMap_5));
						}
					}
					new BytesLimit65535_tMap_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_5", "tMap_5", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row9_tMap_5 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_5__Struct {
				}
				Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out7_tMap_5 = 0;

				out7Struct out7_tmp = new out7Struct();
// ###############################

				/**
				 * [tMap_5 begin ] stop
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
						 * [tMap_5 main ] start
						 */

						currentComponent = "tMap_5";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row9", "tLogCatcher_1", "tLogCatcher_1", "tLogCatcher", "tMap_5", "tMap_5", "tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row9 - " + (row9 == null ? "" : row9.toLogString()));
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;

						// ###############################
						// # Input tables (lookups)

						boolean rejectedInnerJoin_tMap_5 = false;
						boolean mainRowRejected_tMap_5 = false;
						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
							// ###############################
							// # Output tables

							out7 = null;

// # Output table : 'out7'
							count_out7_tMap_5++;

							out7_tmp.execution_ID = context.exeID;
							out7_tmp.process_ID = row9.pid;
							out7_tmp.job_end_status = "End Fail";
							out7_tmp.job_end_time = TalendDate.getCurrentDate();
							out7_tmp.error_message = row9.message;
							out7 = out7_tmp;
							log.debug("tMap_5 - Outputting the record " + count_out7_tMap_5
									+ " of the output table 'out7'.");

// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_5 = false;

						tos_count_tMap_5++;

						/**
						 * [tMap_5 main ] stop
						 */

						/**
						 * [tMap_5 process_data_begin ] start
						 */

						currentComponent = "tMap_5";

						/**
						 * [tMap_5 process_data_begin ] stop
						 */
// Start of branch "out7"
						if (out7 != null) {

							/**
							 * [tDBOutput_6 main ] start
							 */

							currentComponent = "tDBOutput_6";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "out7", "tMap_5", "tMap_5", "tMap", "tDBOutput_6", "tDBOutput_6", "tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("out7 - " + (out7 == null ? "" : out7.toLogString()));
							}

							whetherReject_tDBOutput_6 = false;
							if (out7.process_ID == null) {
								pstmt_tDBOutput_6.setNull(1, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_6.setString(1, out7.process_ID);
							}

							if (out7.job_end_status == null) {
								pstmt_tDBOutput_6.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_6.setString(2, out7.job_end_status);
							}

							if (out7.job_end_time != null) {
								date_tDBOutput_6 = out7.job_end_time.getTime();
								if (date_tDBOutput_6 < year1_tDBOutput_6 || date_tDBOutput_6 >= year10000_tDBOutput_6) {
									pstmt_tDBOutput_6.setString(3, "0000-00-00 00:00:00");
								} else {
									pstmt_tDBOutput_6.setTimestamp(3, new java.sql.Timestamp(date_tDBOutput_6));
								}
							} else {
								pstmt_tDBOutput_6.setNull(3, java.sql.Types.DATE);
							}

							if (out7.error_message == null) {
								pstmt_tDBOutput_6.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_6.setString(4, out7.error_message);
							}

							if (out7.execution_ID == null) {
								pstmt_tDBOutput_6.setNull(5 + count_tDBOutput_6, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_6.setInt(5 + count_tDBOutput_6, out7.execution_ID);
							}

							pstmt_tDBOutput_6.addBatch();
							nb_line_tDBOutput_6++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_6 - " + ("Adding the record ") + (nb_line_tDBOutput_6)
										+ (" to the ") + ("UPDATE") + (" batch."));
							batchSizeCounter_tDBOutput_6++;
							if (batchSize_tDBOutput_6 <= batchSizeCounter_tDBOutput_6) {
								try {
									int countSum_tDBOutput_6 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_6 - " + ("Executing the ") + ("UPDATE") + (" batch."));
									for (int countEach_tDBOutput_6 : pstmt_tDBOutput_6.executeBatch()) {
										countSum_tDBOutput_6 += (countEach_tDBOutput_6 < 0 ? 0 : countEach_tDBOutput_6);
									}
									rowsToCommitCount_tDBOutput_6 += countSum_tDBOutput_6;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_6 - " + ("The ") + ("UPDATE")
												+ (" batch execution has succeeded."));
									updatedCount_tDBOutput_6 += countSum_tDBOutput_6;
									batchSizeCounter_tDBOutput_6 = 0;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_6_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_6 = 0;
									for (int countEach_tDBOutput_6 : e.getUpdateCounts()) {
										countSum_tDBOutput_6 += (countEach_tDBOutput_6 < 0 ? 0 : countEach_tDBOutput_6);
									}
									rowsToCommitCount_tDBOutput_6 += countSum_tDBOutput_6;
									updatedCount_tDBOutput_6 += countSum_tDBOutput_6;
									System.err.println(e.getMessage());
									log.error("tDBOutput_6 - " + (e.getMessage()));
								}
							}

							tos_count_tDBOutput_6++;

							/**
							 * [tDBOutput_6 main ] stop
							 */

							/**
							 * [tDBOutput_6 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_6";

							/**
							 * [tDBOutput_6 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_6 process_data_end ] start
							 */

							currentComponent = "tDBOutput_6";

							/**
							 * [tDBOutput_6 process_data_end ] stop
							 */

						} // End of branch "out7"

						/**
						 * [tMap_5 process_data_end ] start
						 */

						currentComponent = "tMap_5";

						/**
						 * [tMap_5 process_data_end ] stop
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
				 * [tMap_5 end ] start
				 */

				currentComponent = "tMap_5";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_5 - Written records count in the table 'out7': " + count_out7_tMap_5 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row9", 2, 0,
						"tLogCatcher_1", "tLogCatcher_1", "tLogCatcher", "tMap_5", "tMap_5", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_5 - " + ("Done."));

				ok_Hash.put("tMap_5", true);
				end_Hash.put("tMap_5", System.currentTimeMillis());

				/**
				 * [tMap_5 end ] stop
				 */

				/**
				 * [tDBOutput_6 end ] start
				 */

				currentComponent = "tDBOutput_6";

				try {
					if (pstmt_tDBOutput_6 != null) {
						int countSum_tDBOutput_6 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_6 - " + ("Executing the ") + ("UPDATE") + (" batch."));
						for (int countEach_tDBOutput_6 : pstmt_tDBOutput_6.executeBatch()) {
							countSum_tDBOutput_6 += (countEach_tDBOutput_6 < 0 ? 0 : countEach_tDBOutput_6);
						}
						rowsToCommitCount_tDBOutput_6 += countSum_tDBOutput_6;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_6 - " + ("The ") + ("UPDATE") + (" batch execution has succeeded."));

						updatedCount_tDBOutput_6 += countSum_tDBOutput_6;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_6_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_6 = 0;
					for (int countEach_tDBOutput_6 : e.getUpdateCounts()) {
						countSum_tDBOutput_6 += (countEach_tDBOutput_6 < 0 ? 0 : countEach_tDBOutput_6);
					}
					rowsToCommitCount_tDBOutput_6 += countSum_tDBOutput_6;

					updatedCount_tDBOutput_6 += countSum_tDBOutput_6;

					log.error("tDBOutput_6 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}

				if (pstmt_tDBOutput_6 != null) {

					pstmt_tDBOutput_6.close();
					resourceMap.remove("pstmt_tDBOutput_6");

				}

				resourceMap.put("statementClosed_tDBOutput_6", true);

				nb_line_deleted_tDBOutput_6 = nb_line_deleted_tDBOutput_6 + deletedCount_tDBOutput_6;
				nb_line_update_tDBOutput_6 = nb_line_update_tDBOutput_6 + updatedCount_tDBOutput_6;
				nb_line_inserted_tDBOutput_6 = nb_line_inserted_tDBOutput_6 + insertedCount_tDBOutput_6;
				nb_line_rejected_tDBOutput_6 = nb_line_rejected_tDBOutput_6 + rejectedCount_tDBOutput_6;

				globalMap.put("tDBOutput_6_NB_LINE", nb_line_tDBOutput_6);
				globalMap.put("tDBOutput_6_NB_LINE_UPDATED", nb_line_update_tDBOutput_6);
				globalMap.put("tDBOutput_6_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_6);
				globalMap.put("tDBOutput_6_NB_LINE_DELETED", nb_line_deleted_tDBOutput_6);
				globalMap.put("tDBOutput_6_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_6);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out7", 2, 0, "tMap_5",
						"tMap_5", "tMap", "tDBOutput_6", "tDBOutput_6", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_6 - " + ("Done."));

				ok_Hash.put("tDBOutput_6", true);
				end_Hash.put("tDBOutput_6", System.currentTimeMillis());

				/**
				 * [tDBOutput_6 end ] stop
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
				 * [tMap_5 finally ] start
				 */

				currentComponent = "tMap_5";

				/**
				 * [tMap_5 finally ] stop
				 */

				/**
				 * [tDBOutput_6 finally ] start
				 */

				currentComponent = "tDBOutput_6";

				if (resourceMap.get("statementClosed_tDBOutput_6") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_6 = null;
					if ((pstmtToClose_tDBOutput_6 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_6")) != null) {
						pstmtToClose_tDBOutput_6.close();
					}
				}

				/**
				 * [tDBOutput_6 finally ] stop
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

	public void tPostjob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tPostjob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tPostjob_1");
		org.slf4j.MDC.put("_subJobPid", "qel81T_" + subJobPidCounter.getAndIncrement());

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
				 * [tPostjob_1 begin ] start
				 */

				ok_Hash.put("tPostjob_1", false);
				start_Hash.put("tPostjob_1", System.currentTimeMillis());

				currentComponent = "tPostjob_1";

				int tos_count_tPostjob_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tPostjob_1", "tPostjob_1", "tPostjob");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tPostjob_1 begin ] stop
				 */

				/**
				 * [tPostjob_1 main ] start
				 */

				currentComponent = "tPostjob_1";

				tos_count_tPostjob_1++;

				/**
				 * [tPostjob_1 main ] stop
				 */

				/**
				 * [tPostjob_1 process_data_begin ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 process_data_begin ] stop
				 */

				/**
				 * [tPostjob_1 process_data_end ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 process_data_end ] stop
				 */

				/**
				 * [tPostjob_1 end ] start
				 */

				currentComponent = "tPostjob_1";

				ok_Hash.put("tPostjob_1", true);
				end_Hash.put("tPostjob_1", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
				}
				tDBClose_1Process(globalMap);

				/**
				 * [tPostjob_1 end ] stop
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
				 * [tPostjob_1 finally ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tPostjob_1_SUBPROCESS_STATE", 1);
	}

	public void tDBClose_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBClose_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBClose_1");
		org.slf4j.MDC.put("_subJobPid", "AU3VvB_" + subJobPidCounter.getAndIncrement());

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
				 * [tDBClose_1 begin ] start
				 */

				ok_Hash.put("tDBClose_1", false);
				start_Hash.put("tDBClose_1", System.currentTimeMillis());

				currentComponent = "tDBClose_1";

				int tos_count_tDBClose_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBClose_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBClose_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBClose_1 = new StringBuilder();
							log4jParamters_tDBClose_1.append("Parameters:");
							log4jParamters_tDBClose_1
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBClose_1.append(" | ");
							log4jParamters_tDBClose_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlClose");
							log4jParamters_tDBClose_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBClose_1 - " + (log4jParamters_tDBClose_1));
						}
					}
					new BytesLimit65535_tDBClose_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBClose_1", "tDBClose_1", "tMysqlClose");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tDBClose_1 begin ] stop
				 */

				/**
				 * [tDBClose_1 main ] start
				 */

				currentComponent = "tDBClose_1";

				java.sql.Connection conn_tDBClose_1 = (java.sql.Connection) globalMap
						.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_tDBClose_1 != null && !conn_tDBClose_1.isClosed()) {

					log.debug(
							"tDBClose_1 - Closing the connection 'ExecutionLogStart_1_tDBConnection_1' to the database.");

					conn_tDBClose_1.close();

					if ("com.mysql.cj.jdbc.Driver"
							.equals((String) globalMap.get("driverClass_ExecutionLogStart_1_tDBConnection_1"))
							&& routines.system.BundleUtils.inOSGi()) {
						Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").getMethod("checkedShutdown")
								.invoke(null, (Object[]) null);
					}

					log.debug("tDBClose_1 - Connection 'ExecutionLogStart_1_tDBConnection_1' to the database closed.");

				}

				tos_count_tDBClose_1++;

				/**
				 * [tDBClose_1 main ] stop
				 */

				/**
				 * [tDBClose_1 process_data_begin ] start
				 */

				currentComponent = "tDBClose_1";

				/**
				 * [tDBClose_1 process_data_begin ] stop
				 */

				/**
				 * [tDBClose_1 process_data_end ] start
				 */

				currentComponent = "tDBClose_1";

				/**
				 * [tDBClose_1 process_data_end ] stop
				 */

				/**
				 * [tDBClose_1 end ] start
				 */

				currentComponent = "tDBClose_1";

				if (log.isDebugEnabled())
					log.debug("tDBClose_1 - " + ("Done."));

				ok_Hash.put("tDBClose_1", true);
				end_Hash.put("tDBClose_1", System.currentTimeMillis());

				/**
				 * [tDBClose_1 end ] stop
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
				 * [tDBClose_1 finally ] start
				 */

				currentComponent = "tDBClose_1";

				/**
				 * [tDBClose_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBClose_1_SUBPROCESS_STATE", 1);
	}

	public static class out4Struct implements routines.system.IPersistableRow<out4Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];
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
			final out4Struct other = (out4Struct) obj;

			if (this.execution_ID == null) {
				if (other.execution_ID != null)
					return false;

			} else if (!this.execution_ID.equals(other.execution_ID))

				return false;

			return true;
		}

		public void copyDataTo(out4Struct other) {

			other.execution_ID = this.execution_ID;
			other.job_end_status = this.job_end_status;
			other.job_end_time = this.job_end_time;

		}

		public void copyKeysDataTo(out4Struct other) {

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

					this.job_end_status = readString(dis);

					this.job_end_time = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.execution_ID = readInteger(dis);

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
		public int compareTo(out4Struct other) {

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

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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

			return null;

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.newColumn = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		public int compareTo(row6Struct other) {

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
		org.slf4j.MDC.put("_subJobPid", "9MpfPI_" + subJobPidCounter.getAndIncrement());

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

				row6Struct row6 = new row6Struct();
				out4Struct out4 = new out4Struct();

				/**
				 * [tDBOutput_4 begin ] start
				 */

				ok_Hash.put("tDBOutput_4", false);
				start_Hash.put("tDBOutput_4", System.currentTimeMillis());

				currentComponent = "tDBOutput_4";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out4");

				int tos_count_tDBOutput_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_4 = new StringBuilder();
							log4jParamters_tDBOutput_4.append("Parameters:");
							log4jParamters_tDBOutput_4.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("TABLE" + " = " + "\"Execution_Log_Table\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("DATA_ACTION" + " = " + "UPDATE");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USE_FIELD_OPTIONS" + " = " + "true");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("FIELD_OPTIONS" + " = " + "[{UPDATE_KEY=" + ("true")
									+ ", DELETE_KEY=" + ("false") + ", UPDATABLE=" + ("true") + ", INSERTABLE="
									+ ("true") + ", SCHEMA_COLUMN=" + ("execution_ID") + "}, {UPDATE_KEY=" + ("false")
									+ ", DELETE_KEY=" + ("false") + ", UPDATABLE=" + ("true") + ", INSERTABLE="
									+ ("true") + ", SCHEMA_COLUMN=" + ("job_end_status") + "}, {UPDATE_KEY=" + ("false")
									+ ", DELETE_KEY=" + ("false") + ", UPDATABLE=" + ("true") + ", INSERTABLE="
									+ ("true") + ", SCHEMA_COLUMN=" + ("job_end_time") + "}]");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_4 - " + (log4jParamters_tDBOutput_4));
						}
					}
					new BytesLimit65535_tDBOutput_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_4", "tDBOutput_4", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int updateKeyCount_tDBOutput_4 = 1;
				if (updateKeyCount_tDBOutput_4 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_4 == 3 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
				}

				int nb_line_tDBOutput_4 = 0;
				int nb_line_update_tDBOutput_4 = 0;
				int nb_line_inserted_tDBOutput_4 = 0;
				int nb_line_deleted_tDBOutput_4 = 0;
				int nb_line_rejected_tDBOutput_4 = 0;

				int deletedCount_tDBOutput_4 = 0;
				int updatedCount_tDBOutput_4 = 0;
				int insertedCount_tDBOutput_4 = 0;
				int rowsToCommitCount_tDBOutput_4 = 0;
				int rejectedCount_tDBOutput_4 = 0;

				String tableName_tDBOutput_4 = "Execution_Log_Table";
				boolean whetherReject_tDBOutput_4 = false;

				java.util.Calendar calendar_tDBOutput_4 = java.util.Calendar.getInstance();
				calendar_tDBOutput_4.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				calendar_tDBOutput_4.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				long date_tDBOutput_4;

				java.sql.Connection conn_tDBOutput_4 = null;
				conn_tDBOutput_4 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_4.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_4.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_4.getAutoCommit()) + ("'."));
				int batchSize_tDBOutput_4 = 10000;
				int batchSizeCounter_tDBOutput_4 = 0;

				int count_tDBOutput_4 = 0;

				String update_tDBOutput_4 = "UPDATE `" + "Execution_Log_Table"
						+ "` SET `execution_ID` = ?,`job_end_status` = ?,`job_end_time` = ? WHERE `execution_ID` = ?";

				java.sql.PreparedStatement pstmt_tDBOutput_4 = conn_tDBOutput_4.prepareStatement(update_tDBOutput_4);
				resourceMap.put("pstmt_tDBOutput_4", pstmt_tDBOutput_4);

				/**
				 * [tDBOutput_4 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row6");

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
				int count_row6_tMap_2 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out4_tMap_2 = 0;

				out4Struct out4_tmp = new out4Struct();
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
					row6.newColumn = randtRowGenerator_1.getRandomnewColumn();
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

							, "row6", "tRowGenerator_1", "tRowGenerator_1", "tRowGenerator", "tMap_2", "tMap_2", "tMap"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row6 - " + (row6 == null ? "" : row6.toLogString()));
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

						out4 = null;

// # Output table : 'out4'
						count_out4_tMap_2++;

						out4_tmp.execution_ID = context.exeID;
						out4_tmp.job_end_status = "End Success";
						out4_tmp.job_end_time = TalendDate.getCurrentDate();
						out4 = out4_tmp;
						log.debug(
								"tMap_2 - Outputting the record " + count_out4_tMap_2 + " of the output table 'out4'.");

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
// Start of branch "out4"
					if (out4 != null) {

						/**
						 * [tDBOutput_4 main ] start
						 */

						currentComponent = "tDBOutput_4";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "out4", "tMap_2", "tMap_2", "tMap", "tDBOutput_4", "tDBOutput_4", "tMysqlOutput"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("out4 - " + (out4 == null ? "" : out4.toLogString()));
						}

						whetherReject_tDBOutput_4 = false;
						if (out4.execution_ID == null) {
							pstmt_tDBOutput_4.setNull(1, java.sql.Types.INTEGER);
						} else {
							pstmt_tDBOutput_4.setInt(1, out4.execution_ID);
						}

						if (out4.job_end_status == null) {
							pstmt_tDBOutput_4.setNull(2, java.sql.Types.VARCHAR);
						} else {
							pstmt_tDBOutput_4.setString(2, out4.job_end_status);
						}

						if (out4.job_end_time != null) {
							date_tDBOutput_4 = out4.job_end_time.getTime();
							if (date_tDBOutput_4 < year1_tDBOutput_4 || date_tDBOutput_4 >= year10000_tDBOutput_4) {
								pstmt_tDBOutput_4.setString(3, "0000-00-00 00:00:00");
							} else {
								pstmt_tDBOutput_4.setTimestamp(3, new java.sql.Timestamp(date_tDBOutput_4));
							}
						} else {
							pstmt_tDBOutput_4.setNull(3, java.sql.Types.DATE);
						}

						if (out4.execution_ID == null) {
							pstmt_tDBOutput_4.setNull(4 + count_tDBOutput_4, java.sql.Types.INTEGER);
						} else {
							pstmt_tDBOutput_4.setInt(4 + count_tDBOutput_4, out4.execution_ID);
						}

						pstmt_tDBOutput_4.addBatch();
						nb_line_tDBOutput_4++;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("Adding the record ") + (nb_line_tDBOutput_4) + (" to the ")
									+ ("UPDATE") + (" batch."));
						batchSizeCounter_tDBOutput_4++;
						if (batchSize_tDBOutput_4 <= batchSizeCounter_tDBOutput_4) {
							try {
								int countSum_tDBOutput_4 = 0;
								if (log.isDebugEnabled())
									log.debug("tDBOutput_4 - " + ("Executing the ") + ("UPDATE") + (" batch."));
								for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
									countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
								}
								rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
								if (log.isDebugEnabled())
									log.debug("tDBOutput_4 - " + ("The ") + ("UPDATE")
											+ (" batch execution has succeeded."));
								updatedCount_tDBOutput_4 += countSum_tDBOutput_4;
								batchSizeCounter_tDBOutput_4 = 0;
							} catch (java.sql.BatchUpdateException e) {
								globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());
								int countSum_tDBOutput_4 = 0;
								for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
									countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
								}
								rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
								updatedCount_tDBOutput_4 += countSum_tDBOutput_4;
								System.err.println(e.getMessage());
								log.error("tDBOutput_4 - " + (e.getMessage()));
							}
						}

						tos_count_tDBOutput_4++;

						/**
						 * [tDBOutput_4 main ] stop
						 */

						/**
						 * [tDBOutput_4 process_data_begin ] start
						 */

						currentComponent = "tDBOutput_4";

						/**
						 * [tDBOutput_4 process_data_begin ] stop
						 */

						/**
						 * [tDBOutput_4 process_data_end ] start
						 */

						currentComponent = "tDBOutput_4";

						/**
						 * [tDBOutput_4 process_data_end ] stop
						 */

					} // End of branch "out4"

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
				log.debug("tMap_2 - Written records count in the table 'out4': " + count_out4_tMap_2 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row6", 2, 0,
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
				 * [tDBOutput_4 end ] start
				 */

				currentComponent = "tDBOutput_4";

				try {
					if (pstmt_tDBOutput_4 != null) {
						int countSum_tDBOutput_4 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("Executing the ") + ("UPDATE") + (" batch."));
						for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
							countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
						}
						rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("The ") + ("UPDATE") + (" batch execution has succeeded."));

						updatedCount_tDBOutput_4 += countSum_tDBOutput_4;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_4 = 0;
					for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
						countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
					}
					rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

					updatedCount_tDBOutput_4 += countSum_tDBOutput_4;

					log.error("tDBOutput_4 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}

				if (pstmt_tDBOutput_4 != null) {

					pstmt_tDBOutput_4.close();
					resourceMap.remove("pstmt_tDBOutput_4");

				}

				resourceMap.put("statementClosed_tDBOutput_4", true);

				nb_line_deleted_tDBOutput_4 = nb_line_deleted_tDBOutput_4 + deletedCount_tDBOutput_4;
				nb_line_update_tDBOutput_4 = nb_line_update_tDBOutput_4 + updatedCount_tDBOutput_4;
				nb_line_inserted_tDBOutput_4 = nb_line_inserted_tDBOutput_4 + insertedCount_tDBOutput_4;
				nb_line_rejected_tDBOutput_4 = nb_line_rejected_tDBOutput_4 + rejectedCount_tDBOutput_4;

				globalMap.put("tDBOutput_4_NB_LINE", nb_line_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_UPDATED", nb_line_update_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_DELETED", nb_line_deleted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_4);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out4", 2, 0, "tMap_2",
						"tMap_2", "tMap", "tDBOutput_4", "tDBOutput_4", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Done."));

				ok_Hash.put("tDBOutput_4", true);
				end_Hash.put("tDBOutput_4", System.currentTimeMillis());

				/**
				 * [tDBOutput_4 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRowGenerator_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk3", 0, "ok");
			}

			tDBInput_2Process(globalMap);

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
				 * [tDBOutput_4 finally ] start
				 */

				currentComponent = "tDBOutput_4";

				if (resourceMap.get("statementClosed_tDBOutput_4") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_4 = null;
					if ((pstmtToClose_tDBOutput_4 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_4")) != null) {
						pstmtToClose_tDBOutput_4.close();
					}
				}

				/**
				 * [tDBOutput_4 finally ] stop
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

	public static class out6Struct implements routines.system.IPersistableRow<out6Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

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

			return "";

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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.var1 = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

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
		public int compareTo(out6Struct other) {

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

	public static class row7Struct implements routines.system.IPersistableRow<row7Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public int AuditLogID;

		public int getAuditLogID() {
			return this.AuditLogID;
		}

		public Boolean AuditLogIDIsNullable() {
			return false;
		}

		public Boolean AuditLogIDIsKey() {
			return false;
		}

		public Integer AuditLogIDLength() {
			return 19;
		}

		public Integer AuditLogIDPrecision() {
			return 0;
		}

		public String AuditLogIDDefault() {

			return "";

		}

		public String AuditLogIDComment() {

			return "";

		}

		public String AuditLogIDPattern() {

			return "";

		}

		public String AuditLogIDOriginalDbColumnName() {

			return "AuditLogID";

		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.AuditLogID = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.AuditLogID = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.AuditLogID);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.AuditLogID);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("AuditLogID=" + String.valueOf(AuditLogID));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(AuditLogID);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

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

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBInput_2");
		org.slf4j.MDC.put("_subJobPid", "0Xgq5D_" + subJobPidCounter.getAndIncrement());

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

				row7Struct row7 = new row7Struct();
				out6Struct out6 = new out6Struct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out6");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[1];

					public void addRow(String[] row) {

						for (int i = 0; i < 1; i++) {
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
						for (k = 0; k < (totals + 0 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 0 - name.length() - k; i++) {
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

						// last column
						for (int i = 0; i < colLengths[0] - fillChars[0].length() - fillChars[1].length() + 2; i++) {
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
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(new String[] { "var1", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row7");

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
				int count_row7_tMap_3 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
					int var1;
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out6_tMap_3 = 0;

				out6Struct out6_tmp = new out6Struct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
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
							log4jParamters_tDBInput_2.append("TABLE" + " = " + "\"Aduit_Log\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERY" + " = "
									+ "\"select coalesce (max(AuditLogID),0) as AuditLogID from Aduit_Log\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("AuditLogID") + "}]");
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

				String dbquery_tDBInput_2 = "select coalesce (max(AuditLogID),0) as AuditLogID from Aduit_Log";

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
							row7.AuditLogID = 0;
						} else {

							row7.AuditLogID = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
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
						 * [tMap_3 main ] start
						 */

						currentComponent = "tMap_3";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row7", "tDBInput_2", "tDBInput_2", "tMysqlInput", "tMap_3", "tMap_3", "tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row7 - " + (row7 == null ? "" : row7.toLogString()));
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

							Var__tMap_3__Struct Var = Var__tMap_3;
							Var.var1 = context.exeID = row7.AuditLogID + 1;// ###############################
							// ###############################
							// # Output tables

							out6 = null;

// # Output table : 'out6'
							count_out6_tMap_3++;

							out6_tmp.var1 = Var.var1;
							out6 = out6_tmp;
							log.debug("tMap_3 - Outputting the record " + count_out6_tMap_3
									+ " of the output table 'out6'.");

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
// Start of branch "out6"
						if (out6 != null) {

							/**
							 * [tLogRow_1 main ] start
							 */

							currentComponent = "tLogRow_1";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "out6", "tMap_3", "tMap_3", "tMap", "tLogRow_1", "tLogRow_1", "tLogRow"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("out6 - " + (out6 == null ? "" : out6.toLogString()));
							}

///////////////////////		

							String[] row_tLogRow_1 = new String[1];

							row_tLogRow_1[0] = String.valueOf(out6.var1);

							util_tLogRow_1.addRow(row_tLogRow_1);
							nb_line_tLogRow_1++;
							log.info("tLogRow_1 - Content of row " + nb_line_tLogRow_1 + ": "
									+ TalendString.unionString("|", row_tLogRow_1));
//////

//////                    

///////////////////////    			

							tos_count_tLogRow_1++;

							/**
							 * [tLogRow_1 main ] stop
							 */

							/**
							 * [tLogRow_1 process_data_begin ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_begin ] stop
							 */

							/**
							 * [tLogRow_1 process_data_end ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_end ] stop
							 */

						} // End of branch "out6"

						/**
						 * [tMap_3 process_data_end ] start
						 */

						currentComponent = "tMap_3";

						/**
						 * [tMap_3 process_data_end ] stop
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
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_3 - Written records count in the table 'out6': " + count_out6_tMap_3 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row7", 2, 0,
						"tDBInput_2", "tDBInput_2", "tMysqlInput", "tMap_3", "tMap_3", "tMap", "output")) {
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
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out6", 2, 0, "tMap_3",
						"tMap_3", "tMap", "tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBInput_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tFileInputDelimited_1Process(globalMap);

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
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
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

	public static class out5Struct implements routines.system.IPersistableRow<out5Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public Integer AuditLogID;

		public Integer getAuditLogID() {
			return this.AuditLogID;
		}

		public Boolean AuditLogIDIsNullable() {
			return true;
		}

		public Boolean AuditLogIDIsKey() {
			return false;
		}

		public Integer AuditLogIDLength() {
			return null;
		}

		public Integer AuditLogIDPrecision() {
			return null;
		}

		public String AuditLogIDDefault() {

			return null;

		}

		public String AuditLogIDComment() {

			return "";

		}

		public String AuditLogIDPattern() {

			return "";

		}

		public String AuditLogIDOriginalDbColumnName() {

			return "AuditLogID";

		}

		public String FileName;

		public String getFileName() {
			return this.FileName;
		}

		public Boolean FileNameIsNullable() {
			return true;
		}

		public Boolean FileNameIsKey() {
			return false;
		}

		public Integer FileNameLength() {
			return null;
		}

		public Integer FileNamePrecision() {
			return null;
		}

		public String FileNameDefault() {

			return null;

		}

		public String FileNameComment() {

			return "";

		}

		public String FileNamePattern() {

			return "";

		}

		public String FileNameOriginalDbColumnName() {

			return "FileName";

		}

		public Integer NumberOfRecords;

		public Integer getNumberOfRecords() {
			return this.NumberOfRecords;
		}

		public Boolean NumberOfRecordsIsNullable() {
			return true;
		}

		public Boolean NumberOfRecordsIsKey() {
			return false;
		}

		public Integer NumberOfRecordsLength() {
			return null;
		}

		public Integer NumberOfRecordsPrecision() {
			return null;
		}

		public String NumberOfRecordsDefault() {

			return null;

		}

		public String NumberOfRecordsComment() {

			return "";

		}

		public String NumberOfRecordsPattern() {

			return "";

		}

		public String NumberOfRecordsOriginalDbColumnName() {

			return "NumberOfRecords";

		}

		public java.util.Date DateCreated;

		public java.util.Date getDateCreated() {
			return this.DateCreated;
		}

		public Boolean DateCreatedIsNullable() {
			return true;
		}

		public Boolean DateCreatedIsKey() {
			return false;
		}

		public Integer DateCreatedLength() {
			return null;
		}

		public Integer DateCreatedPrecision() {
			return null;
		}

		public String DateCreatedDefault() {

			return null;

		}

		public String DateCreatedComment() {

			return "";

		}

		public String DateCreatedPattern() {

			return "dd-MM-yyyy";

		}

		public String DateCreatedOriginalDbColumnName() {

			return "DateCreated";

		}

		public Integer StageFail;

		public Integer getStageFail() {
			return this.StageFail;
		}

		public Boolean StageFailIsNullable() {
			return true;
		}

		public Boolean StageFailIsKey() {
			return false;
		}

		public Integer StageFailLength() {
			return null;
		}

		public Integer StageFailPrecision() {
			return null;
		}

		public String StageFailDefault() {

			return null;

		}

		public String StageFailComment() {

			return "";

		}

		public String StageFailPattern() {

			return "";

		}

		public String StageFailOriginalDbColumnName() {

			return "StageFail";

		}

		public java.util.Date ETLDate;

		public java.util.Date getETLDate() {
			return this.ETLDate;
		}

		public Boolean ETLDateIsNullable() {
			return true;
		}

		public Boolean ETLDateIsKey() {
			return false;
		}

		public Integer ETLDateLength() {
			return null;
		}

		public Integer ETLDatePrecision() {
			return null;
		}

		public String ETLDateDefault() {

			return null;

		}

		public String ETLDateComment() {

			return "";

		}

		public String ETLDatePattern() {

			return "dd-MM-yyyy";

		}

		public String ETLDateOriginalDbColumnName() {

			return "ETLDate";

		}

		public Integer StagePass;

		public Integer getStagePass() {
			return this.StagePass;
		}

		public Boolean StagePassIsNullable() {
			return true;
		}

		public Boolean StagePassIsKey() {
			return false;
		}

		public Integer StagePassLength() {
			return null;
		}

		public Integer StagePassPrecision() {
			return null;
		}

		public String StagePassDefault() {

			return null;

		}

		public String StagePassComment() {

			return "";

		}

		public String StagePassPattern() {

			return "";

		}

		public String StagePassOriginalDbColumnName() {

			return "StagePass";

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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.AuditLogID = readInteger(dis);

					this.FileName = readString(dis);

					this.NumberOfRecords = readInteger(dis);

					this.DateCreated = readDate(dis);

					this.StageFail = readInteger(dis);

					this.ETLDate = readDate(dis);

					this.StagePass = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.AuditLogID = readInteger(dis);

					this.FileName = readString(dis);

					this.NumberOfRecords = readInteger(dis);

					this.DateCreated = readDate(dis);

					this.StageFail = readInteger(dis);

					this.ETLDate = readDate(dis);

					this.StagePass = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.AuditLogID, dos);

				// String

				writeString(this.FileName, dos);

				// Integer

				writeInteger(this.NumberOfRecords, dos);

				// java.util.Date

				writeDate(this.DateCreated, dos);

				// Integer

				writeInteger(this.StageFail, dos);

				// java.util.Date

				writeDate(this.ETLDate, dos);

				// Integer

				writeInteger(this.StagePass, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.AuditLogID, dos);

				// String

				writeString(this.FileName, dos);

				// Integer

				writeInteger(this.NumberOfRecords, dos);

				// java.util.Date

				writeDate(this.DateCreated, dos);

				// Integer

				writeInteger(this.StageFail, dos);

				// java.util.Date

				writeDate(this.ETLDate, dos);

				// Integer

				writeInteger(this.StagePass, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("AuditLogID=" + String.valueOf(AuditLogID));
			sb.append(",FileName=" + FileName);
			sb.append(",NumberOfRecords=" + String.valueOf(NumberOfRecords));
			sb.append(",DateCreated=" + String.valueOf(DateCreated));
			sb.append(",StageFail=" + String.valueOf(StageFail));
			sb.append(",ETLDate=" + String.valueOf(ETLDate));
			sb.append(",StagePass=" + String.valueOf(StagePass));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (AuditLogID == null) {
				sb.append("<null>");
			} else {
				sb.append(AuditLogID);
			}

			sb.append("|");

			if (FileName == null) {
				sb.append("<null>");
			} else {
				sb.append(FileName);
			}

			sb.append("|");

			if (NumberOfRecords == null) {
				sb.append("<null>");
			} else {
				sb.append(NumberOfRecords);
			}

			sb.append("|");

			if (DateCreated == null) {
				sb.append("<null>");
			} else {
				sb.append(DateCreated);
			}

			sb.append("|");

			if (StageFail == null) {
				sb.append("<null>");
			} else {
				sb.append(StageFail);
			}

			sb.append("|");

			if (ETLDate == null) {
				sb.append("<null>");
			} else {
				sb.append(ETLDate);
			}

			sb.append("|");

			if (StagePass == null) {
				sb.append("<null>");
			} else {
				sb.append(StagePass);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out5Struct other) {

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

	public static class row8Struct implements routines.system.IPersistableRow<row8Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_stage = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_stage = new byte[0];

		public String FileName;

		public String getFileName() {
			return this.FileName;
		}

		public Boolean FileNameIsNullable() {
			return true;
		}

		public Boolean FileNameIsKey() {
			return false;
		}

		public Integer FileNameLength() {
			return null;
		}

		public Integer FileNamePrecision() {
			return null;
		}

		public String FileNameDefault() {

			return null;

		}

		public String FileNameComment() {

			return "";

		}

		public String FileNamePattern() {

			return "";

		}

		public String FileNameOriginalDbColumnName() {

			return "FileName";

		}

		public Integer NumberOfRecords;

		public Integer getNumberOfRecords() {
			return this.NumberOfRecords;
		}

		public Boolean NumberOfRecordsIsNullable() {
			return true;
		}

		public Boolean NumberOfRecordsIsKey() {
			return false;
		}

		public Integer NumberOfRecordsLength() {
			return null;
		}

		public Integer NumberOfRecordsPrecision() {
			return null;
		}

		public String NumberOfRecordsDefault() {

			return null;

		}

		public String NumberOfRecordsComment() {

			return "";

		}

		public String NumberOfRecordsPattern() {

			return "";

		}

		public String NumberOfRecordsOriginalDbColumnName() {

			return "NumberOfRecords";

		}

		public java.util.Date DateCreated;

		public java.util.Date getDateCreated() {
			return this.DateCreated;
		}

		public Boolean DateCreatedIsNullable() {
			return true;
		}

		public Boolean DateCreatedIsKey() {
			return false;
		}

		public Integer DateCreatedLength() {
			return null;
		}

		public Integer DateCreatedPrecision() {
			return null;
		}

		public String DateCreatedDefault() {

			return null;

		}

		public String DateCreatedComment() {

			return "";

		}

		public String DateCreatedPattern() {

			return "dd-MM-yyyy";

		}

		public String DateCreatedOriginalDbColumnName() {

			return "DateCreated";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_stage.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_stage.length == 0) {
						commonByteArray_FINALPROJECT_stage = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_stage = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_stage, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_stage, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.FileName = readString(dis);

					this.NumberOfRecords = readInteger(dis);

					this.DateCreated = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_stage) {

				try {

					int length = 0;

					this.FileName = readString(dis);

					this.NumberOfRecords = readInteger(dis);

					this.DateCreated = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.FileName, dos);

				// Integer

				writeInteger(this.NumberOfRecords, dos);

				// java.util.Date

				writeDate(this.DateCreated, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.FileName, dos);

				// Integer

				writeInteger(this.NumberOfRecords, dos);

				// java.util.Date

				writeDate(this.DateCreated, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("FileName=" + FileName);
			sb.append(",NumberOfRecords=" + String.valueOf(NumberOfRecords));
			sb.append(",DateCreated=" + String.valueOf(DateCreated));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (FileName == null) {
				sb.append("<null>");
			} else {
				sb.append(FileName);
			}

			sb.append("|");

			if (NumberOfRecords == null) {
				sb.append("<null>");
			} else {
				sb.append(NumberOfRecords);
			}

			sb.append("|");

			if (DateCreated == null) {
				sb.append("<null>");
			} else {
				sb.append(DateCreated);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row8Struct other) {

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

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tFileInputDelimited_1");
		org.slf4j.MDC.put("_subJobPid", "bdhioW_" + subJobPidCounter.getAndIncrement());

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

				row8Struct row8 = new row8Struct();
				out5Struct out5 = new out5Struct();

				/**
				 * [tDBOutput_5 begin ] start
				 */

				ok_Hash.put("tDBOutput_5", false);
				start_Hash.put("tDBOutput_5", System.currentTimeMillis());

				currentComponent = "tDBOutput_5";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out5");

				int tos_count_tDBOutput_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_5 = new StringBuilder();
							log4jParamters_tDBOutput_5.append("Parameters:");
							log4jParamters_tDBOutput_5.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("TABLE" + " = " + "\"Aduit_Log\"");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_5.append(" | ");
							log4jParamters_tDBOutput_5.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_5 - " + (log4jParamters_tDBOutput_5));
						}
					}
					new BytesLimit65535_tDBOutput_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_5", "tDBOutput_5", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_5 = 0;
				int nb_line_update_tDBOutput_5 = 0;
				int nb_line_inserted_tDBOutput_5 = 0;
				int nb_line_deleted_tDBOutput_5 = 0;
				int nb_line_rejected_tDBOutput_5 = 0;

				int deletedCount_tDBOutput_5 = 0;
				int updatedCount_tDBOutput_5 = 0;
				int insertedCount_tDBOutput_5 = 0;
				int rowsToCommitCount_tDBOutput_5 = 0;
				int rejectedCount_tDBOutput_5 = 0;

				String tableName_tDBOutput_5 = "Aduit_Log";
				boolean whetherReject_tDBOutput_5 = false;

				java.util.Calendar calendar_tDBOutput_5 = java.util.Calendar.getInstance();
				calendar_tDBOutput_5.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_5 = calendar_tDBOutput_5.getTime().getTime();
				calendar_tDBOutput_5.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_5 = calendar_tDBOutput_5.getTime().getTime();
				long date_tDBOutput_5;

				java.sql.Connection conn_tDBOutput_5 = null;
				conn_tDBOutput_5 = (java.sql.Connection) globalMap.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_5 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_5.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_5.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_5 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_5.getAutoCommit()) + ("'."));

				int count_tDBOutput_5 = 0;

				String insert_tDBOutput_5 = "INSERT INTO `" + "Aduit_Log"
						+ "` (`AuditLogID`,`FileName`,`NumberOfRecords`,`DateCreated`,`StageFail`,`ETLDate`,`StagePass`) VALUES (?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_5 = 100;
				int batchSizeCounter_tDBOutput_5 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_5 = conn_tDBOutput_5.prepareStatement(insert_tDBOutput_5);
				resourceMap.put("pstmt_tDBOutput_5", pstmt_tDBOutput_5);

				/**
				 * [tDBOutput_5 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				ok_Hash.put("tMap_4", false);
				start_Hash.put("tMap_4", System.currentTimeMillis());

				currentComponent = "tMap_4";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row8");

				int tos_count_tMap_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_4 = new StringBuilder();
							log4jParamters_tMap_4.append("Parameters:");
							log4jParamters_tMap_4.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_4 - " + (log4jParamters_tMap_4));
						}
					}
					new BytesLimit65535_tMap_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_4", "tMap_4", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row8_tMap_4 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
					int var1;
					int var2;
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out5_tMap_4 = 0;

				out5Struct out5_tmp = new out5Struct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
							log4jParamters_tFileInputDelimited_1.append("Parameters:");
							log4jParamters_tFileInputDelimited_1.append("USE_EXISTING_DYNAMIC" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1
									.append("FILENAME" + " = " + "\"/Users/admin/Desktop/RealTimeFiles/History.txt\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSV_OPTION" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FIELDSEPARATOR" + " = " + "\" \"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("HEADER" + " = " + "1");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FOOTER" + " = " + "0");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("REMOVE_EMPTY_ROW" + " = " + "true");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("UNCOMPRESS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("RANDOM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMALL" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append(
									"TRIMSELECT" + " = " + "[{TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("FileName")
											+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("NumberOfRecords")
											+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("DateCreated") + "}]");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_FIELDS_NUM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_DATE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("SPLITRECORD" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENABLE_DECODE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("USE_HEADER_AS_IS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputDelimited_1 - " + (log4jParamters_tFileInputDelimited_1));
						}
					}
					new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited");
					talendJobLogProcess(globalMap);
				}

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				int limit_tFileInputDelimited_1 = -1;
				try {

					Object filename_tFileInputDelimited_1 = "/Users/admin/Desktop/RealTimeFiles/History.txt";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0 || random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"/Users/admin/Desktop/RealTimeFiles/History.txt", "ISO-8859-15", " ", "\n", true, 1, 0,
								limit_tFileInputDelimited_1, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					}

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (fid_tFileInputDelimited_1 != null && fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row8 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row8 = new row8Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_1 = 0;

							row8.FileName = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 1;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row8.NumberOfRecords = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"NumberOfRecords", "row8", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row8.NumberOfRecords = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 2;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row8.DateCreated = ParserUtils.parseTo_Date(temp, "dd-MM-yyyy");

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"DateCreated", "row8", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row8.DateCreated = null;

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - " + e.getMessage());

							System.err.println(e.getMessage());
							row8 = null;

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record "
								+ fid_tFileInputDelimited_1.getRowNumber() + ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row8"
						if (row8 != null) {

							/**
							 * [tMap_4 main ] start
							 */

							currentComponent = "tMap_4";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row8", "tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited",
									"tMap_4", "tMap_4", "tMap"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row8 - " + (row8 == null ? "" : row8.toLogString()));
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

							// ###############################
							// # Input tables (lookups)

							boolean rejectedInnerJoin_tMap_4 = false;
							boolean mainRowRejected_tMap_4 = false;
							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_4__Struct Var = Var__tMap_4;
								Var.var1 = context.stagepass = ((Integer) globalMap.get("tDBOutput_1_NB_LINE"));
								Var.var2 = context.stagefail = ((Integer) globalMap.get("tDBInput_1_NB_LINE"))
										- ((Integer) globalMap.get("tDBOutput_1_NB_LINE"));// ###############################
								// ###############################
								// # Output tables

								out5 = null;

// # Output table : 'out5'
								count_out5_tMap_4++;

								out5_tmp.AuditLogID = context.exeID;
								out5_tmp.FileName = row8.FileName;
								out5_tmp.NumberOfRecords = row8.NumberOfRecords;
								out5_tmp.DateCreated = row8.DateCreated;
								out5_tmp.StageFail = Var.var2;
								out5_tmp.ETLDate = TalendDate.getCurrentDate();
								out5_tmp.StagePass = Var.var1;
								out5 = out5_tmp;
								log.debug("tMap_4 - Outputting the record " + count_out5_tMap_4
										+ " of the output table 'out5'.");

// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_4 = false;

							tos_count_tMap_4++;

							/**
							 * [tMap_4 main ] stop
							 */

							/**
							 * [tMap_4 process_data_begin ] start
							 */

							currentComponent = "tMap_4";

							/**
							 * [tMap_4 process_data_begin ] stop
							 */
// Start of branch "out5"
							if (out5 != null) {

								/**
								 * [tDBOutput_5 main ] start
								 */

								currentComponent = "tDBOutput_5";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "out5", "tMap_4", "tMap_4", "tMap", "tDBOutput_5", "tDBOutput_5",
										"tMysqlOutput"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("out5 - " + (out5 == null ? "" : out5.toLogString()));
								}

								whetherReject_tDBOutput_5 = false;
								if (out5.AuditLogID == null) {
									pstmt_tDBOutput_5.setNull(1, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(1, out5.AuditLogID);
								}

								if (out5.FileName == null) {
									pstmt_tDBOutput_5.setNull(2, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_5.setString(2, out5.FileName);
								}

								if (out5.NumberOfRecords == null) {
									pstmt_tDBOutput_5.setNull(3, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(3, out5.NumberOfRecords);
								}

								if (out5.DateCreated != null) {
									date_tDBOutput_5 = out5.DateCreated.getTime();
									if (date_tDBOutput_5 < year1_tDBOutput_5
											|| date_tDBOutput_5 >= year10000_tDBOutput_5) {
										pstmt_tDBOutput_5.setString(4, "0000-00-00 00:00:00");
									} else {
										pstmt_tDBOutput_5.setTimestamp(4, new java.sql.Timestamp(date_tDBOutput_5));
									}
								} else {
									pstmt_tDBOutput_5.setNull(4, java.sql.Types.DATE);
								}

								if (out5.StageFail == null) {
									pstmt_tDBOutput_5.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(5, out5.StageFail);
								}

								if (out5.ETLDate != null) {
									date_tDBOutput_5 = out5.ETLDate.getTime();
									if (date_tDBOutput_5 < year1_tDBOutput_5
											|| date_tDBOutput_5 >= year10000_tDBOutput_5) {
										pstmt_tDBOutput_5.setString(6, "0000-00-00 00:00:00");
									} else {
										pstmt_tDBOutput_5.setTimestamp(6, new java.sql.Timestamp(date_tDBOutput_5));
									}
								} else {
									pstmt_tDBOutput_5.setNull(6, java.sql.Types.DATE);
								}

								if (out5.StagePass == null) {
									pstmt_tDBOutput_5.setNull(7, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(7, out5.StagePass);
								}

								pstmt_tDBOutput_5.addBatch();
								nb_line_tDBOutput_5++;

								if (log.isDebugEnabled())
									log.debug("tDBOutput_5 - " + ("Adding the record ") + (nb_line_tDBOutput_5)
											+ (" to the ") + ("INSERT") + (" batch."));
								batchSizeCounter_tDBOutput_5++;
								if (batchSize_tDBOutput_5 <= batchSizeCounter_tDBOutput_5) {
									try {
										int countSum_tDBOutput_5 = 0;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_5 - " + ("Executing the ") + ("INSERT") + (" batch."));
										for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
											countSum_tDBOutput_5 += (countEach_tDBOutput_5 == java.sql.Statement.EXECUTE_FAILED
													? 0
													: 1);
										}
										rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_5 - " + ("The ") + ("INSERT")
													+ (" batch execution has succeeded."));
										insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
									} catch (java.sql.BatchUpdateException e) {
										globalMap.put("tDBOutput_5_ERROR_MESSAGE", e.getMessage());
										int countSum_tDBOutput_5 = 0;
										for (int countEach_tDBOutput_5 : e.getUpdateCounts()) {
											countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0
													: countEach_tDBOutput_5);
										}
										rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
										insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
										System.err.println(e.getMessage());
										log.error("tDBOutput_5 - " + (e.getMessage()));
									}

									batchSizeCounter_tDBOutput_5 = 0;
								}

								tos_count_tDBOutput_5++;

								/**
								 * [tDBOutput_5 main ] stop
								 */

								/**
								 * [tDBOutput_5 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_5";

								/**
								 * [tDBOutput_5 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_5 process_data_end ] start
								 */

								currentComponent = "tDBOutput_5";

								/**
								 * [tDBOutput_5 process_data_end ] stop
								 */

							} // End of branch "out5"

							/**
							 * [tMap_4 process_data_end ] start
							 */

							currentComponent = "tMap_4";

							/**
							 * [tMap_4 process_data_end ] stop
							 */

						} // End of branch "row8"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("/Users/admin/Desktop/RealTimeFiles/History.txt") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", fid_tFileInputDelimited_1.getRowNumber());

						log.info("tFileInputDelimited_1 - Retrieved records count: "
								+ fid_tFileInputDelimited_1.getRowNumber() + ".");

					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				currentComponent = "tMap_4";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_4 - Written records count in the table 'out5': " + count_out5_tMap_4 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row8", 2, 0,
						"tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited", "tMap_4", "tMap_4",
						"tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_4 - " + ("Done."));

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tDBOutput_5 end ] start
				 */

				currentComponent = "tDBOutput_5";

				try {
					if (batchSizeCounter_tDBOutput_5 != 0) {
						int countSum_tDBOutput_5 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_5 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
							countSum_tDBOutput_5 += (countEach_tDBOutput_5 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_5 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_5 = 0;
					for (int countEach_tDBOutput_5 : e.getUpdateCounts()) {
						countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
					}
					rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

					insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

					log.error("tDBOutput_5 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_5 = 0;

				if (pstmt_tDBOutput_5 != null) {

					pstmt_tDBOutput_5.close();
					resourceMap.remove("pstmt_tDBOutput_5");

				}

				resourceMap.put("statementClosed_tDBOutput_5", true);

				nb_line_deleted_tDBOutput_5 = nb_line_deleted_tDBOutput_5 + deletedCount_tDBOutput_5;
				nb_line_update_tDBOutput_5 = nb_line_update_tDBOutput_5 + updatedCount_tDBOutput_5;
				nb_line_inserted_tDBOutput_5 = nb_line_inserted_tDBOutput_5 + insertedCount_tDBOutput_5;
				nb_line_rejected_tDBOutput_5 = nb_line_rejected_tDBOutput_5 + rejectedCount_tDBOutput_5;

				globalMap.put("tDBOutput_5_NB_LINE", nb_line_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_UPDATED", nb_line_update_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_DELETED", nb_line_deleted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_5);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out5", 2, 0, "tMap_4",
						"tMap_4", "tMap", "tDBOutput_5", "tDBOutput_5", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_5 - " + ("Done."));

				ok_Hash.put("tDBOutput_5", true);
				end_Hash.put("tDBOutput_5", System.currentTimeMillis());

				/**
				 * [tDBOutput_5 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputDelimited_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk4", 0, "ok");
			}

			tDBCommit_2Process(globalMap);

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
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				currentComponent = "tMap_4";

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tDBOutput_5 finally ] start
				 */

				currentComponent = "tDBOutput_5";

				if (resourceMap.get("statementClosed_tDBOutput_5") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_5 = null;
					if ((pstmtToClose_tDBOutput_5 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_5")) != null) {
						pstmtToClose_tDBOutput_5.close();
					}
				}

				/**
				 * [tDBOutput_5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public void tDBCommit_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBCommit_2");
		org.slf4j.MDC.put("_subJobPid", "V6obNQ_" + subJobPidCounter.getAndIncrement());

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
				 * [tDBCommit_2 begin ] start
				 */

				ok_Hash.put("tDBCommit_2", false);
				start_Hash.put("tDBCommit_2", System.currentTimeMillis());

				currentComponent = "tDBCommit_2";

				int tos_count_tDBCommit_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBCommit_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBCommit_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBCommit_2 = new StringBuilder();
							log4jParamters_tDBCommit_2.append("Parameters:");
							log4jParamters_tDBCommit_2
									.append("CONNECTION" + " = " + "ExecutionLogStart_1_tDBConnection_1");
							log4jParamters_tDBCommit_2.append(" | ");
							log4jParamters_tDBCommit_2.append("CLOSE" + " = " + "true");
							log4jParamters_tDBCommit_2.append(" | ");
							log4jParamters_tDBCommit_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlCommit");
							log4jParamters_tDBCommit_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBCommit_2 - " + (log4jParamters_tDBCommit_2));
						}
					}
					new BytesLimit65535_tDBCommit_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBCommit_2", "tDBCommit_2", "tMysqlCommit");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tDBCommit_2 begin ] stop
				 */

				/**
				 * [tDBCommit_2 main ] start
				 */

				currentComponent = "tDBCommit_2";

				java.sql.Connection conn_tDBCommit_2 = (java.sql.Connection) globalMap
						.get("conn_ExecutionLogStart_1_tDBConnection_1");

				if (conn_tDBCommit_2 != null && !conn_tDBCommit_2.isClosed()) {

					try {

						log.debug("tDBCommit_2 - Connection 'ExecutionLogStart_1_tDBConnection_1' starting to commit.");

						conn_tDBCommit_2.commit();

						log.debug(
								"tDBCommit_2 - Connection 'ExecutionLogStart_1_tDBConnection_1' commit has succeeded.");

					} finally {

						log.debug(
								"tDBCommit_2 - Closing the connection 'ExecutionLogStart_1_tDBConnection_1' to the database.");

						conn_tDBCommit_2.close();

						if ("com.mysql.cj.jdbc.Driver"
								.equals((String) globalMap.get("driverClass_ExecutionLogStart_1_tDBConnection_1"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug(
								"tDBCommit_2 - Connection 'ExecutionLogStart_1_tDBConnection_1' to the database closed.");

					}

				}

				tos_count_tDBCommit_2++;

				/**
				 * [tDBCommit_2 main ] stop
				 */

				/**
				 * [tDBCommit_2 process_data_begin ] start
				 */

				currentComponent = "tDBCommit_2";

				/**
				 * [tDBCommit_2 process_data_begin ] stop
				 */

				/**
				 * [tDBCommit_2 process_data_end ] start
				 */

				currentComponent = "tDBCommit_2";

				/**
				 * [tDBCommit_2 process_data_end ] stop
				 */

				/**
				 * [tDBCommit_2 end ] start
				 */

				currentComponent = "tDBCommit_2";

				if (log.isDebugEnabled())
					log.debug("tDBCommit_2 - " + ("Done."));

				ok_Hash.put("tDBCommit_2", true);
				end_Hash.put("tDBCommit_2", System.currentTimeMillis());

				/**
				 * [tDBCommit_2 end ] stop
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
				 * [tDBCommit_2 finally ] start
				 */

				currentComponent = "tDBCommit_2";

				/**
				 * [tDBCommit_2 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBCommit_2_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "talendJobLog");
		org.slf4j.MDC.put("_subJobPid", "ZiY5hC_" + subJobPidCounter.getAndIncrement());

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
		final stage stageClass = new stage();

		int exitCode = stageClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'stage' - Done.");
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
		log.info("TalendJob: 'stage' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_z2UYoN4WEe6TK8DHHwW1PQ");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-03-23T06:34:46.367962Z");

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
			java.io.InputStream inContext = stage.class.getClassLoader()
					.getResourceAsStream("finalproject/stage_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = stage.class.getClassLoader()
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
					context.setContextType("stagepass", "id_Integer");
					if (context.getStringValue("stagepass") == null) {
						context.stagepass = null;
					} else {
						try {
							context.stagepass = routines.system.ParserUtils
									.parseTo_Integer(context.getProperty("stagepass"));
						} catch (NumberFormatException e) {
							log.warn(String.format("Null value will be used for context parameter %s: %s", "stagepass",
									e.getMessage()));
							context.stagepass = null;
						}
					}
					context.setContextType("stagefail", "id_Integer");
					if (context.getStringValue("stagefail") == null) {
						context.stagefail = null;
					} else {
						try {
							context.stagefail = routines.system.ParserUtils
									.parseTo_Integer(context.getProperty("stagefail"));
						} catch (NumberFormatException e) {
							log.warn(String.format("Null value will be used for context parameter %s: %s", "stagefail",
									e.getMessage()));
							context.stagefail = null;
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
			if (parentContextMap.containsKey("stagepass")) {
				context.stagepass = (Integer) parentContextMap.get("stagepass");
			}
			if (parentContextMap.containsKey("stagefail")) {
				context.stagefail = (Integer) parentContextMap.get("stagefail");
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
		log.info("TalendJob: 'stage' - Started.");
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
		try {
			errorCode = null;
			tRowGenerator_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tRowGenerator_1) {
			globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", -1);

			e_tRowGenerator_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		try {
			errorCode = null;
			tPostjob_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tPostjob_1) {
			globalMap.put("tPostjob_1_SUBPROCESS_STATE", -1);

			e_tPostjob_1.printStackTrace();

		}

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : stage");
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
		log.info("TalendJob: 'stage' - Finished - status: " + status + " returnCode: " + returnCode);

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
 * 897493 characters generated by Talend Cloud Data Management Platform on the
 * 23 March 2024 at 12:04:46 PM IST
 ************************************************************************************************/