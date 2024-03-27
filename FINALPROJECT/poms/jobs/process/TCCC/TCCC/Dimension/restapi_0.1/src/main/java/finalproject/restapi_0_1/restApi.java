
package finalproject.restapi_0_1;

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

//the import part of tJava_1
//import java.util.List;

@SuppressWarnings("unused")

/**
 * Job: restApi Purpose: <br>
 * Description: <br>
 * 
 * @author R, Sahitya
 * @version 8.0.1.20240222_1049-patch
 * @status
 */
public class restApi implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "restApi.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(restApi.class);

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

	private Object[] multiThreadLockWrite = new Object[0];

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

			if (url != null) {

				this.setProperty("url", url.toString());

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

		public String url;

		public String getUrl() {
			return this.url;
		}
	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "restApi";
	private final String projectName = "FINALPROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Object>());

	private final java.util.Map<String, Long> start_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Long>());
	private final java.util.Map<String, Long> end_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Long>());
	private final java.util.Map<String, Boolean> ok_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Boolean>());
	public final java.util.List<String[]> globalBuffer = java.util.Collections
			.synchronizedList(new java.util.ArrayList<String[]>());

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_RG3BAOs9Ee6o-JQyryMKwA", "0.1");
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
					restApi.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(restApi.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputJSON_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tFileInputJSON_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tFileInputJSON_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tJava_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tJava_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tREST_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tREST_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tExtractJSONFields_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tREST_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tREST_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputJSON_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tJava_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tREST_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_restApi = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_restApi = new byte[0];

		public String name;

		public String getName() {
			return this.name;
		}

		public Boolean nameIsNullable() {
			return true;
		}

		public Boolean nameIsKey() {
			return false;
		}

		public Integer nameLength() {
			return null;
		}

		public Integer namePrecision() {
			return null;
		}

		public String nameDefault() {

			return null;

		}

		public String nameComment() {

			return "";

		}

		public String namePattern() {

			return "";

		}

		public String nameOriginalDbColumnName() {

			return "name";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("name=" + name);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (name == null) {
				sb.append("<null>");
			} else {
				sb.append(name);
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

	public void tFileInputJSON_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputJSON_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tFileInputJSON_3");
		org.slf4j.MDC.put("_subJobPid", "VtddbM_" + subJobPidCounter.getAndIncrement());

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

				/**
				 * [tLogRow_5 begin ] start
				 */

				ok_Hash.put("tLogRow_5", false);
				start_Hash.put("tLogRow_5", System.currentTimeMillis());

				currentComponent = "tLogRow_5";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row6");

				int tos_count_tLogRow_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_5 = new StringBuilder();
							log4jParamters_tLogRow_5.append("Parameters:");
							log4jParamters_tLogRow_5.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_5.append(" | ");
							log4jParamters_tLogRow_5.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_5.append(" | ");
							log4jParamters_tLogRow_5.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_5.append(" | ");
							log4jParamters_tLogRow_5.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_5 - " + (log4jParamters_tLogRow_5));
						}
					}
					new BytesLimit65535_tLogRow_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_5", "tLogRow_5", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_tLogRow_5 {

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
				Util_tLogRow_5 util_tLogRow_5 = new Util_tLogRow_5();
				util_tLogRow_5.setTableName("tLogRow_5");
				util_tLogRow_5.addRow(new String[] { "name", });
				StringBuilder strBuffer_tLogRow_5 = null;
				int nb_line_tLogRow_5 = 0;
///////////////////////    			

				/**
				 * [tLogRow_5 begin ] stop
				 */

				/**
				 * [tFileInputJSON_3 begin ] start
				 */

				ok_Hash.put("tFileInputJSON_3", false);
				start_Hash.put("tFileInputJSON_3", System.currentTimeMillis());

				currentComponent = "tFileInputJSON_3";

				int tos_count_tFileInputJSON_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputJSON_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputJSON_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputJSON_3 = new StringBuilder();
							log4jParamters_tFileInputJSON_3.append("Parameters:");
							log4jParamters_tFileInputJSON_3.append("READ_BY" + " = " + "JSONPATH");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("JSON_PATH_VERSION" + " = " + "2_1_0");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("USEURL" + " = " + "false");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3
									.append("FILENAME" + " = " + "\"/Users/admin/Desktop/RealTimeFiles/people.json\"");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("JSON_LOOP_QUERY" + " = " + "\"$.[*]\"");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("MAPPING_JSONPATH" + " = " + "[{QUERY="
									+ ("\"name\"") + ", SCHEMA_COLUMN=" + ("name") + "}]");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("USE_LOOP_AS_ROOT" + " = " + "true");
							log4jParamters_tFileInputJSON_3.append(" | ");
							log4jParamters_tFileInputJSON_3.append("ENCODING" + " = " + "\"UTF-8\"");
							log4jParamters_tFileInputJSON_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputJSON_3 - " + (log4jParamters_tFileInputJSON_3));
						}
					}
					new BytesLimit65535_tFileInputJSON_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputJSON_3", "tFileInputJSON_3", "tFileInputJSON");
					talendJobLogProcess(globalMap);
				}

				class JsonPathCache_tFileInputJSON_3 {
					final java.util.Map<String, com.jayway.jsonpath.JsonPath> jsonPathString2compiledJsonPath = new java.util.HashMap<String, com.jayway.jsonpath.JsonPath>();

					public com.jayway.jsonpath.JsonPath getCompiledJsonPath(String jsonPath) {
						if (jsonPathString2compiledJsonPath.containsKey(jsonPath)) {
							return jsonPathString2compiledJsonPath.get(jsonPath);
						} else {
							com.jayway.jsonpath.JsonPath compiledLoopPath = com.jayway.jsonpath.JsonPath
									.compile(jsonPath);
							jsonPathString2compiledJsonPath.put(jsonPath, compiledLoopPath);
							return compiledLoopPath;
						}
					}
				}

				int nb_line_tFileInputJSON_3 = 0;

				JsonPathCache_tFileInputJSON_3 jsonPathCache_tFileInputJSON_3 = new JsonPathCache_tFileInputJSON_3();

				String loopPath_tFileInputJSON_3 = "$.[*]";
				java.util.List<Object> resultset_tFileInputJSON_3 = new java.util.ArrayList<Object>();

				java.io.InputStream is_tFileInputJSON_3 = null;
				com.jayway.jsonpath.ParseContext parseContext_tFileInputJSON_3 = com.jayway.jsonpath.JsonPath
						.using(com.jayway.jsonpath.Configuration.defaultConfiguration());
				Object filenameOrStream_tFileInputJSON_3 = null;
				try {
					filenameOrStream_tFileInputJSON_3 = "/Users/admin/Desktop/RealTimeFiles/people.json";
				} catch (java.lang.Exception e_tFileInputJSON_3) {
					globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());

					log.error("tFileInputJSON_3 - " + e_tFileInputJSON_3.getMessage());

					globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
					System.err.println(e_tFileInputJSON_3.getMessage());
				}

				com.jayway.jsonpath.ReadContext document_tFileInputJSON_3 = null;
				try {
					if (filenameOrStream_tFileInputJSON_3 instanceof java.io.InputStream) {
						is_tFileInputJSON_3 = (java.io.InputStream) filenameOrStream_tFileInputJSON_3;
					} else {

						is_tFileInputJSON_3 = new java.io.FileInputStream((String) filenameOrStream_tFileInputJSON_3);

					}

					document_tFileInputJSON_3 = parseContext_tFileInputJSON_3.parse(is_tFileInputJSON_3, "UTF-8");
					com.jayway.jsonpath.JsonPath compiledLoopPath_tFileInputJSON_3 = jsonPathCache_tFileInputJSON_3
							.getCompiledJsonPath(loopPath_tFileInputJSON_3);
					Object result_tFileInputJSON_3 = document_tFileInputJSON_3.read(compiledLoopPath_tFileInputJSON_3,
							net.minidev.json.JSONObject.class);
					if (result_tFileInputJSON_3 instanceof net.minidev.json.JSONArray) {
						resultset_tFileInputJSON_3 = (net.minidev.json.JSONArray) result_tFileInputJSON_3;
					} else {
						resultset_tFileInputJSON_3.add(result_tFileInputJSON_3);
					}
				} catch (java.lang.Exception e_tFileInputJSON_3) {
					globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
					log.error("tFileInputJSON_3 - " + e_tFileInputJSON_3.getMessage());

					globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
					System.err.println(e_tFileInputJSON_3.getMessage());
				} finally {
					if (is_tFileInputJSON_3 != null) {
						is_tFileInputJSON_3.close();
					}
				}

				String jsonPath_tFileInputJSON_3 = null;
				com.jayway.jsonpath.JsonPath compiledJsonPath_tFileInputJSON_3 = null;

				Object value_tFileInputJSON_3 = null;
				log.info("tFileInputJSON_3 - Retrieving records from data.");
				Object root_tFileInputJSON_3 = null;
				for (Object row_tFileInputJSON_3 : resultset_tFileInputJSON_3) {
					nb_line_tFileInputJSON_3++;
					log.debug("tFileInputJSON_3 - Retrieving the record " + (nb_line_tFileInputJSON_3) + ".");

					row6 = null;
					boolean whetherReject_tFileInputJSON_3 = false;
					row6 = new row6Struct();

					try {
						jsonPath_tFileInputJSON_3 = "name";
						compiledJsonPath_tFileInputJSON_3 = jsonPathCache_tFileInputJSON_3
								.getCompiledJsonPath(jsonPath_tFileInputJSON_3);

						try {

							value_tFileInputJSON_3 = compiledJsonPath_tFileInputJSON_3.read(row_tFileInputJSON_3);

							row6.name = value_tFileInputJSON_3 == null ?

									null : value_tFileInputJSON_3.toString();
						} catch (com.jayway.jsonpath.PathNotFoundException e_tFileInputJSON_3) {
							globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
							row6.name =

									null;
						}
					} catch (java.lang.Exception e_tFileInputJSON_3) {
						globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
						whetherReject_tFileInputJSON_3 = true;
						log.error("tFileInputJSON_3 - " + e_tFileInputJSON_3.getMessage());

						System.err.println(e_tFileInputJSON_3.getMessage());
						row6 = null;
						globalMap.put("tFileInputJSON_3_ERROR_MESSAGE", e_tFileInputJSON_3.getMessage());
					}
//}

					/**
					 * [tFileInputJSON_3 begin ] stop
					 */

					/**
					 * [tFileInputJSON_3 main ] start
					 */

					currentComponent = "tFileInputJSON_3";

					tos_count_tFileInputJSON_3++;

					/**
					 * [tFileInputJSON_3 main ] stop
					 */

					/**
					 * [tFileInputJSON_3 process_data_begin ] start
					 */

					currentComponent = "tFileInputJSON_3";

					/**
					 * [tFileInputJSON_3 process_data_begin ] stop
					 */
// Start of branch "row6"
					if (row6 != null) {

						/**
						 * [tLogRow_5 main ] start
						 */

						currentComponent = "tLogRow_5";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row6", "tFileInputJSON_3", "tFileInputJSON_3", "tFileInputJSON", "tLogRow_5",
								"tLogRow_5", "tLogRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row6 - " + (row6 == null ? "" : row6.toLogString()));
						}

///////////////////////		

						String[] row_tLogRow_5 = new String[1];

						if (row6.name != null) { //
							row_tLogRow_5[0] = String.valueOf(row6.name);

						} //

						util_tLogRow_5.addRow(row_tLogRow_5);
						nb_line_tLogRow_5++;
						log.info("tLogRow_5 - Content of row " + nb_line_tLogRow_5 + ": "
								+ TalendString.unionString("|", row_tLogRow_5));
//////

//////                    

///////////////////////    			

						tos_count_tLogRow_5++;

						/**
						 * [tLogRow_5 main ] stop
						 */

						/**
						 * [tLogRow_5 process_data_begin ] start
						 */

						currentComponent = "tLogRow_5";

						/**
						 * [tLogRow_5 process_data_begin ] stop
						 */

						/**
						 * [tLogRow_5 process_data_end ] start
						 */

						currentComponent = "tLogRow_5";

						/**
						 * [tLogRow_5 process_data_end ] stop
						 */

					} // End of branch "row6"

					/**
					 * [tFileInputJSON_3 process_data_end ] start
					 */

					currentComponent = "tFileInputJSON_3";

					/**
					 * [tFileInputJSON_3 process_data_end ] stop
					 */

					/**
					 * [tFileInputJSON_3 end ] start
					 */

					currentComponent = "tFileInputJSON_3";

				}
				globalMap.put("tFileInputJSON_3_NB_LINE", nb_line_tFileInputJSON_3);
				log.debug("tFileInputJSON_3 - Retrieved records count: " + nb_line_tFileInputJSON_3 + " .");

				if (log.isDebugEnabled())
					log.debug("tFileInputJSON_3 - " + ("Done."));

				ok_Hash.put("tFileInputJSON_3", true);
				end_Hash.put("tFileInputJSON_3", System.currentTimeMillis());

				/**
				 * [tFileInputJSON_3 end ] stop
				 */

				/**
				 * [tLogRow_5 end ] start
				 */

				currentComponent = "tLogRow_5";

//////

				java.io.PrintStream consoleOut_tLogRow_5 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_5 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_5 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_5);
				}

				consoleOut_tLogRow_5.println(util_tLogRow_5.format().toString());
				consoleOut_tLogRow_5.flush();
//////
				globalMap.put("tLogRow_5_NB_LINE", nb_line_tLogRow_5);
				if (log.isInfoEnabled())
					log.info("tLogRow_5 - " + ("Printed row count: ") + (nb_line_tLogRow_5) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row6", 2, 0,
						"tFileInputJSON_3", "tFileInputJSON_3", "tFileInputJSON", "tLogRow_5", "tLogRow_5", "tLogRow",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_5 - " + ("Done."));

				ok_Hash.put("tLogRow_5", true);
				end_Hash.put("tLogRow_5", System.currentTimeMillis());

				/**
				 * [tLogRow_5 end ] stop
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
				 * [tFileInputJSON_3 finally ] start
				 */

				currentComponent = "tFileInputJSON_3";

				/**
				 * [tFileInputJSON_3 finally ] stop
				 */

				/**
				 * [tLogRow_5 finally ] start
				 */

				currentComponent = "tLogRow_5";

				/**
				 * [tLogRow_5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputJSON_3_SUBPROCESS_STATE", 1);
	}

	public void tJava_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tJava_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tJava_1");
		org.slf4j.MDC.put("_subJobPid", "Qzicau_" + subJobPidCounter.getAndIncrement());

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
				 * [tJava_1 begin ] start
				 */

				ok_Hash.put("tJava_1", false);
				start_Hash.put("tJava_1", System.currentTimeMillis());

				currentComponent = "tJava_1";

				int tos_count_tJava_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tJava_1", "tJava_1", "tJava");
					talendJobLogProcess(globalMap);
				}

				System.out.println("The Job starts :" + TalendDate.getCurrentDate());
				String host = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?";
				String apikey = "api-key=mA90tAhlP14w4PQEpLAvDjwtgcjD7j3A&";
				String secretkey = "secret-key=gpMhKBTKIiT6GQkn";
				context.url = host + apikey + secretkey;
				System.out.println(context.url);
				System.out.println("The rest API is starting");

				/**
				 * [tJava_1 begin ] stop
				 */

				/**
				 * [tJava_1 main ] start
				 */

				currentComponent = "tJava_1";

				tos_count_tJava_1++;

				/**
				 * [tJava_1 main ] stop
				 */

				/**
				 * [tJava_1 process_data_begin ] start
				 */

				currentComponent = "tJava_1";

				/**
				 * [tJava_1 process_data_begin ] stop
				 */

				/**
				 * [tJava_1 process_data_end ] start
				 */

				currentComponent = "tJava_1";

				/**
				 * [tJava_1 process_data_end ] stop
				 */

				/**
				 * [tJava_1 end ] start
				 */

				currentComponent = "tJava_1";

				ok_Hash.put("tJava_1", true);
				end_Hash.put("tJava_1", System.currentTimeMillis());

				/**
				 * [tJava_1 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tJava_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tREST_2Process(globalMap);

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
				 * [tJava_1 finally ] start
				 */

				currentComponent = "tJava_1";

				/**
				 * [tJava_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tJava_1_SUBPROCESS_STATE", 1);
	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_restApi = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_restApi = new byte[0];

		public Integer rank;

		public Integer getRank() {
			return this.rank;
		}

		public Boolean rankIsNullable() {
			return true;
		}

		public Boolean rankIsKey() {
			return false;
		}

		public Integer rankLength() {
			return null;
		}

		public Integer rankPrecision() {
			return null;
		}

		public String rankDefault() {

			return null;

		}

		public String rankComment() {

			return "";

		}

		public String rankPattern() {

			return "";

		}

		public String rankOriginalDbColumnName() {

			return "rank";

		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public Boolean titleIsNullable() {
			return true;
		}

		public Boolean titleIsKey() {
			return false;
		}

		public Integer titleLength() {
			return null;
		}

		public Integer titlePrecision() {
			return null;
		}

		public String titleDefault() {

			return null;

		}

		public String titleComment() {

			return "";

		}

		public String titlePattern() {

			return "";

		}

		public String titleOriginalDbColumnName() {

			return "title";

		}

		public String author;

		public String getAuthor() {
			return this.author;
		}

		public Boolean authorIsNullable() {
			return true;
		}

		public Boolean authorIsKey() {
			return false;
		}

		public Integer authorLength() {
			return null;
		}

		public Integer authorPrecision() {
			return null;
		}

		public String authorDefault() {

			return null;

		}

		public String authorComment() {

			return "";

		}

		public String authorPattern() {

			return "";

		}

		public String authorOriginalDbColumnName() {

			return "author";

		}

		public Float price;

		public Float getPrice() {
			return this.price;
		}

		public Boolean priceIsNullable() {
			return true;
		}

		public Boolean priceIsKey() {
			return false;
		}

		public Integer priceLength() {
			return null;
		}

		public Integer pricePrecision() {
			return null;
		}

		public String priceDefault() {

			return null;

		}

		public String priceComment() {

			return "";

		}

		public String pricePattern() {

			return "";

		}

		public String priceOriginalDbColumnName() {

			return "price";

		}

		public String publisher;

		public String getPublisher() {
			return this.publisher;
		}

		public Boolean publisherIsNullable() {
			return true;
		}

		public Boolean publisherIsKey() {
			return false;
		}

		public Integer publisherLength() {
			return null;
		}

		public Integer publisherPrecision() {
			return null;
		}

		public String publisherDefault() {

			return null;

		}

		public String publisherComment() {

			return "";

		}

		public String publisherPattern() {

			return "";

		}

		public String publisherOriginalDbColumnName() {

			return "publisher";

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
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.rank = readInteger(dis);

					this.title = readString(dis);

					this.author = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.price = null;
					} else {
						this.price = dis.readFloat();
					}

					this.publisher = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.rank = readInteger(dis);

					this.title = readString(dis);

					this.author = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.price = null;
					} else {
						this.price = dis.readFloat();
					}

					this.publisher = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.rank, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.author, dos);

				// Float

				if (this.price == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.price);
				}

				// String

				writeString(this.publisher, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.rank, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.author, dos);

				// Float

				if (this.price == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.price);
				}

				// String

				writeString(this.publisher, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("rank=" + String.valueOf(rank));
			sb.append(",title=" + title);
			sb.append(",author=" + author);
			sb.append(",price=" + String.valueOf(price));
			sb.append(",publisher=" + publisher);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (rank == null) {
				sb.append("<null>");
			} else {
				sb.append(rank);
			}

			sb.append("|");

			if (title == null) {
				sb.append("<null>");
			} else {
				sb.append(title);
			}

			sb.append("|");

			if (author == null) {
				sb.append("<null>");
			} else {
				sb.append(author);
			}

			sb.append("|");

			if (price == null) {
				sb.append("<null>");
			} else {
				sb.append(price);
			}

			sb.append("|");

			if (publisher == null) {
				sb.append("<null>");
			} else {
				sb.append(publisher);
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

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_FINALPROJECT_restApi = new byte[0];
		static byte[] commonByteArray_FINALPROJECT_restApi = new byte[0];

		public String Body;

		public String getBody() {
			return this.Body;
		}

		public Boolean BodyIsNullable() {
			return true;
		}

		public Boolean BodyIsKey() {
			return false;
		}

		public Integer BodyLength() {
			return 0;
		}

		public Integer BodyPrecision() {
			return 0;
		}

		public String BodyDefault() {

			return "";

		}

		public String BodyComment() {

			return null;

		}

		public String BodyPattern() {

			return null;

		}

		public String BodyOriginalDbColumnName() {

			return "Body";

		}

		public Integer ERROR_CODE;

		public Integer getERROR_CODE() {
			return this.ERROR_CODE;
		}

		public Boolean ERROR_CODEIsNullable() {
			return true;
		}

		public Boolean ERROR_CODEIsKey() {
			return false;
		}

		public Integer ERROR_CODELength() {
			return 0;
		}

		public Integer ERROR_CODEPrecision() {
			return 0;
		}

		public String ERROR_CODEDefault() {

			return "";

		}

		public String ERROR_CODEComment() {

			return null;

		}

		public String ERROR_CODEPattern() {

			return null;

		}

		public String ERROR_CODEOriginalDbColumnName() {

			return "ERROR_CODE";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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
				if (length > commonByteArray_FINALPROJECT_restApi.length) {
					if (length < 1024 && commonByteArray_FINALPROJECT_restApi.length == 0) {
						commonByteArray_FINALPROJECT_restApi = new byte[1024];
					} else {
						commonByteArray_FINALPROJECT_restApi = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_FINALPROJECT_restApi, 0, length);
				strReturn = new String(commonByteArray_FINALPROJECT_restApi, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.Body = readString(dis);

					this.ERROR_CODE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_FINALPROJECT_restApi) {

				try {

					int length = 0;

					this.Body = readString(dis);

					this.ERROR_CODE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Body, dos);

				// Integer

				writeInteger(this.ERROR_CODE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Body, dos);

				// Integer

				writeInteger(this.ERROR_CODE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Body=" + Body);
			sb.append(",ERROR_CODE=" + String.valueOf(ERROR_CODE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Body == null) {
				sb.append("<null>");
			} else {
				sb.append(Body);
			}

			sb.append("|");

			if (ERROR_CODE == null) {
				sb.append("<null>");
			} else {
				sb.append(ERROR_CODE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

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

	public void tREST_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tREST_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tREST_2");
		org.slf4j.MDC.put("_subJobPid", "saDRIF_" + subJobPidCounter.getAndIncrement());

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
				row5Struct row5 = new row5Struct();

				/**
				 * [tLogRow_4 begin ] start
				 */

				ok_Hash.put("tLogRow_4", false);
				start_Hash.put("tLogRow_4", System.currentTimeMillis());

				currentComponent = "tLogRow_4";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row5");

				int tos_count_tLogRow_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_4 = new StringBuilder();
							log4jParamters_tLogRow_4.append("Parameters:");
							log4jParamters_tLogRow_4.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_4.append(" | ");
							log4jParamters_tLogRow_4.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_4.append(" | ");
							log4jParamters_tLogRow_4.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_4.append(" | ");
							log4jParamters_tLogRow_4.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_4 - " + (log4jParamters_tLogRow_4));
						}
					}
					new BytesLimit65535_tLogRow_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_4", "tLogRow_4", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_tLogRow_4 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[5];

					public void addRow(String[] row) {

						for (int i = 0; i < 5; i++) {
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
						for (k = 0; k < (totals + 4 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 4 - name.length() - k; i++) {
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

						// last column
						for (int i = 0; i < colLengths[4] - fillChars[1].length() + 1; i++) {
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
				Util_tLogRow_4 util_tLogRow_4 = new Util_tLogRow_4();
				util_tLogRow_4.setTableName("tLogRow_4");
				util_tLogRow_4.addRow(new String[] { "rank", "title", "author", "price", "publisher", });
				StringBuilder strBuffer_tLogRow_4 = null;
				int nb_line_tLogRow_4 = 0;
///////////////////////    			

				/**
				 * [tLogRow_4 begin ] stop
				 */

				/**
				 * [tExtractJSONFields_2 begin ] start
				 */

				ok_Hash.put("tExtractJSONFields_2", false);
				start_Hash.put("tExtractJSONFields_2", System.currentTimeMillis());

				currentComponent = "tExtractJSONFields_2";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row4");

				int tos_count_tExtractJSONFields_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tExtractJSONFields_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tExtractJSONFields_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tExtractJSONFields_2 = new StringBuilder();
							log4jParamters_tExtractJSONFields_2.append("Parameters:");
							log4jParamters_tExtractJSONFields_2.append("READ_BY" + " = " + "JSONPATH");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2.append("JSON_PATH_VERSION" + " = " + "2_1_0");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2.append("JSONFIELD" + " = " + "Body");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2
									.append("JSON_LOOP_QUERY" + " = " + "\"$.results.books[*]\"");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2.append("MAPPING_4_JSONPATH" + " = " + "[{QUERY="
									+ ("\"rank\"") + ", SCHEMA_COLUMN=" + ("rank") + "}, {QUERY=" + ("\"title\"")
									+ ", SCHEMA_COLUMN=" + ("title") + "}, {QUERY=" + ("\"author\"")
									+ ", SCHEMA_COLUMN=" + ("author") + "}, {QUERY=" + ("\"price\"")
									+ ", SCHEMA_COLUMN=" + ("price") + "}, {QUERY=" + ("\"publisher\"")
									+ ", SCHEMA_COLUMN=" + ("publisher") + "}]");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							log4jParamters_tExtractJSONFields_2.append("USE_LOOP_AS_ROOT" + " = " + "true");
							log4jParamters_tExtractJSONFields_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tExtractJSONFields_2 - " + (log4jParamters_tExtractJSONFields_2));
						}
					}
					new BytesLimit65535_tExtractJSONFields_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tExtractJSONFields_2", "tExtractJSONFields_2", "tExtractJSONFields");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tExtractJSONFields_2 = 0;
				String jsonStr_tExtractJSONFields_2 = "";

				class JsonPathCache_tExtractJSONFields_2 {
					final java.util.Map<String, com.jayway.jsonpath.JsonPath> jsonPathString2compiledJsonPath = new java.util.HashMap<String, com.jayway.jsonpath.JsonPath>();

					public com.jayway.jsonpath.JsonPath getCompiledJsonPath(String jsonPath) {
						if (jsonPathString2compiledJsonPath.containsKey(jsonPath)) {
							return jsonPathString2compiledJsonPath.get(jsonPath);
						} else {
							com.jayway.jsonpath.JsonPath compiledLoopPath = com.jayway.jsonpath.JsonPath
									.compile(jsonPath);
							jsonPathString2compiledJsonPath.put(jsonPath, compiledLoopPath);
							return compiledLoopPath;
						}
					}
				}

				JsonPathCache_tExtractJSONFields_2 jsonPathCache_tExtractJSONFields_2 = new JsonPathCache_tExtractJSONFields_2();

				/**
				 * [tExtractJSONFields_2 begin ] stop
				 */

				/**
				 * [tREST_2 begin ] start
				 */

				ok_Hash.put("tREST_2", false);
				start_Hash.put("tREST_2", System.currentTimeMillis());

				currentComponent = "tREST_2";

				int tos_count_tREST_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tREST_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tREST_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tREST_2 = new StringBuilder();
							log4jParamters_tREST_2.append("Parameters:");
							log4jParamters_tREST_2.append("URL" + " = " + "context.url");
							log4jParamters_tREST_2.append(" | ");
							log4jParamters_tREST_2.append("METHOD" + " = " + "GET");
							log4jParamters_tREST_2.append(" | ");
							log4jParamters_tREST_2.append("HEADERS" + " = " + "[]");
							log4jParamters_tREST_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tREST_2 - " + (log4jParamters_tREST_2));
						}
					}
					new BytesLimit65535_tREST_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tREST_2", "tREST_2", "tREST");
					talendJobLogProcess(globalMap);
				}

				String endpoint_tREST_2 = context.url;

				String trustStoreFile_tREST_2 = System.getProperty("javax.net.ssl.trustStore");
				String trustStoreType_tREST_2 = System.getProperty("javax.net.ssl.trustStoreType");
				String trustStorePWD_tREST_2 = System.getProperty("javax.net.ssl.trustStorePassword");

				String keyStoreFile_tREST_2 = System.getProperty("javax.net.ssl.keyStore");
				String keyStoreType_tREST_2 = System.getProperty("javax.net.ssl.keyStoreType");
				String keyStorePWD_tREST_2 = System.getProperty("javax.net.ssl.keyStorePassword");

				com.sun.jersey.api.client.config.ClientConfig config_tREST_2 = new com.sun.jersey.api.client.config.DefaultClientConfig();

				// APPINT-33909: add entitiy providers (for OSGi deployment)
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.StringProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.ByteArrayProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.FileProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.InputStreamProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.DataSourceProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.MimeMultipartProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.FormProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.ReaderProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.DocumentProvider.class);
				config_tREST_2.getClasses().add(com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider.class);

				javax.net.ssl.SSLContext ctx_tREST_2 = javax.net.ssl.SSLContext.getInstance("SSL");

				javax.net.ssl.TrustManager[] tms_tREST_2 = null;
				if (trustStoreFile_tREST_2 != null && trustStoreType_tREST_2 != null) {
					char[] password_tREST_2 = null;
					if (trustStorePWD_tREST_2 != null)
						password_tREST_2 = trustStorePWD_tREST_2.toCharArray();
					java.security.KeyStore trustStore_tREST_2 = java.security.KeyStore
							.getInstance(trustStoreType_tREST_2);
					trustStore_tREST_2.load(new java.io.FileInputStream(trustStoreFile_tREST_2), password_tREST_2);

					javax.net.ssl.TrustManagerFactory tmf_tREST_2 = javax.net.ssl.TrustManagerFactory
							.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
					tmf_tREST_2.init(trustStore_tREST_2);
					tms_tREST_2 = tmf_tREST_2.getTrustManagers();
				}

				javax.net.ssl.KeyManager[] kms_tREST_2 = null;
				if (keyStoreFile_tREST_2 != null && keyStoreType_tREST_2 != null) {
					char[] password_tREST_2 = null;
					if (keyStorePWD_tREST_2 != null)
						password_tREST_2 = keyStorePWD_tREST_2.toCharArray();
					java.security.KeyStore keyStore_tREST_2 = java.security.KeyStore.getInstance(keyStoreType_tREST_2);
					keyStore_tREST_2.load(new java.io.FileInputStream(keyStoreFile_tREST_2), password_tREST_2);

					javax.net.ssl.KeyManagerFactory kmf_tREST_2 = javax.net.ssl.KeyManagerFactory
							.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
					kmf_tREST_2.init(keyStore_tREST_2, password_tREST_2);
					kms_tREST_2 = kmf_tREST_2.getKeyManagers();
				}

				ctx_tREST_2.init(kms_tREST_2, tms_tREST_2, null);
				config_tREST_2.getProperties().put(
						com.sun.jersey.client.urlconnection.HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
						new com.sun.jersey.client.urlconnection.HTTPSProperties(new javax.net.ssl.HostnameVerifier() {

							public boolean verify(String hostName, javax.net.ssl.SSLSession session) {
								return true;
							}
						}, ctx_tREST_2));

				com.sun.jersey.api.client.Client restClient_tREST_2 = com.sun.jersey.api.client.Client
						.create(config_tREST_2);

				java.util.Map<String, Object> headers_tREST_2 = new java.util.HashMap<String, Object>();

				Object transfer_encoding_tREST_2 = headers_tREST_2.get("Transfer-Encoding");
				if (transfer_encoding_tREST_2 != null && "chunked".equals(transfer_encoding_tREST_2)) {
					restClient_tREST_2.setChunkedEncodingSize(4096);
				}

				com.sun.jersey.api.client.WebResource restResource_tREST_2;
				if (endpoint_tREST_2 != null && !("").equals(endpoint_tREST_2)) {
					restResource_tREST_2 = restClient_tREST_2.resource(endpoint_tREST_2);
				} else {
					throw new IllegalArgumentException("url can't be empty!");
				}

				com.sun.jersey.api.client.ClientResponse errorResponse_tREST_2 = null;
				String restResponse_tREST_2 = "";
				try {

					if (log.isInfoEnabled())
						log.info("tREST_2 - " + ("Prepare to send request to rest server."));
					com.sun.jersey.api.client.WebResource.Builder builder_tREST_2 = null;
					for (java.util.Map.Entry<String, Object> header_tREST_2 : headers_tREST_2.entrySet()) {
						if (builder_tREST_2 == null) {
							builder_tREST_2 = restResource_tREST_2.header(header_tREST_2.getKey(),
									header_tREST_2.getValue());
						} else {
							builder_tREST_2.header(header_tREST_2.getKey(), header_tREST_2.getValue());
						}
					}

					if (builder_tREST_2 != null) {
						restResponse_tREST_2 = builder_tREST_2.get(String.class);
					} else {
						restResponse_tREST_2 = restResource_tREST_2.get(String.class);
					}

				} catch (com.sun.jersey.api.client.UniformInterfaceException ue) {
					globalMap.put("tREST_2_ERROR_MESSAGE", ue.getMessage());
					errorResponse_tREST_2 = ue.getResponse();
				}

				if (log.isInfoEnabled())
					log.info("tREST_2 - " + ("Has sent request to rest server."));
				// for output

				row4 = new row4Struct();
				if (errorResponse_tREST_2 != null) {
					row4.ERROR_CODE = errorResponse_tREST_2.getStatus();
					if (row4.ERROR_CODE != 204) {
						row4.Body = errorResponse_tREST_2.getEntity(String.class);
					}
				} else {
					row4.Body = restResponse_tREST_2;
				}

				/**
				 * [tREST_2 begin ] stop
				 */

				/**
				 * [tREST_2 main ] start
				 */

				currentComponent = "tREST_2";

				tos_count_tREST_2++;

				/**
				 * [tREST_2 main ] stop
				 */

				/**
				 * [tREST_2 process_data_begin ] start
				 */

				currentComponent = "tREST_2";

				/**
				 * [tREST_2 process_data_begin ] stop
				 */

				/**
				 * [tExtractJSONFields_2 main ] start
				 */

				currentComponent = "tExtractJSONFields_2";

				if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

						, "row4", "tREST_2", "tREST_2", "tREST", "tExtractJSONFields_2", "tExtractJSONFields_2",
						"tExtractJSONFields"

				)) {
					talendJobLogProcess(globalMap);
				}

				if (log.isTraceEnabled()) {
					log.trace("row4 - " + (row4 == null ? "" : row4.toLogString()));
				}

				if (row4.Body != null) {// C_01
					jsonStr_tExtractJSONFields_2 = row4.Body.toString();

					row5 = null;

					String loopPath_tExtractJSONFields_2 = "$.results.books[*]";
					java.util.List<Object> resultset_tExtractJSONFields_2 = new java.util.ArrayList<Object>();

					boolean isStructError_tExtractJSONFields_2 = true;
					com.jayway.jsonpath.ReadContext document_tExtractJSONFields_2 = null;
					try {
						document_tExtractJSONFields_2 = com.jayway.jsonpath.JsonPath
								.parse(jsonStr_tExtractJSONFields_2);
						com.jayway.jsonpath.JsonPath compiledLoopPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
								.getCompiledJsonPath(loopPath_tExtractJSONFields_2);
						Object result_tExtractJSONFields_2 = document_tExtractJSONFields_2
								.read(compiledLoopPath_tExtractJSONFields_2, net.minidev.json.JSONObject.class);
						if (result_tExtractJSONFields_2 instanceof net.minidev.json.JSONArray) {
							resultset_tExtractJSONFields_2 = (net.minidev.json.JSONArray) result_tExtractJSONFields_2;
						} else {
							resultset_tExtractJSONFields_2.add(result_tExtractJSONFields_2);
						}

						isStructError_tExtractJSONFields_2 = false;
					} catch (java.lang.Exception ex_tExtractJSONFields_2) {
						globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE", ex_tExtractJSONFields_2.getMessage());
						log.error("tExtractJSONFields_2 - " + ex_tExtractJSONFields_2.getMessage());
						System.err.println(ex_tExtractJSONFields_2.getMessage());
					}

					String jsonPath_tExtractJSONFields_2 = null;
					com.jayway.jsonpath.JsonPath compiledJsonPath_tExtractJSONFields_2 = null;

					Object value_tExtractJSONFields_2 = null;

					Object root_tExtractJSONFields_2 = null;
					for (int i_tExtractJSONFields_2 = 0; isStructError_tExtractJSONFields_2
							|| (i_tExtractJSONFields_2 < resultset_tExtractJSONFields_2
									.size()); i_tExtractJSONFields_2++) {
						if (!isStructError_tExtractJSONFields_2) {
							Object row_tExtractJSONFields_2 = resultset_tExtractJSONFields_2
									.get(i_tExtractJSONFields_2);
							row5 = null;
							row5 = new row5Struct();
							nb_line_tExtractJSONFields_2++;
							try {
								jsonPath_tExtractJSONFields_2 = "rank";
								compiledJsonPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
										.getCompiledJsonPath(jsonPath_tExtractJSONFields_2);

								try {

									value_tExtractJSONFields_2 = compiledJsonPath_tExtractJSONFields_2
											.read(row_tExtractJSONFields_2);

									if (value_tExtractJSONFields_2 != null
											&& !value_tExtractJSONFields_2.toString().isEmpty()) {
										row5.rank = ParserUtils.parseTo_Integer(value_tExtractJSONFields_2.toString());
									} else {
										row5.rank =

												null

										;
									}
								} catch (com.jayway.jsonpath.PathNotFoundException e_tExtractJSONFields_2) {
									globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
											e_tExtractJSONFields_2.getMessage());
									row5.rank =

											null

									;
								}
								jsonPath_tExtractJSONFields_2 = "title";
								compiledJsonPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
										.getCompiledJsonPath(jsonPath_tExtractJSONFields_2);

								try {

									value_tExtractJSONFields_2 = compiledJsonPath_tExtractJSONFields_2
											.read(row_tExtractJSONFields_2);

									row5.title = value_tExtractJSONFields_2 == null ?

											null

											: value_tExtractJSONFields_2.toString();
								} catch (com.jayway.jsonpath.PathNotFoundException e_tExtractJSONFields_2) {
									globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
											e_tExtractJSONFields_2.getMessage());
									row5.title =

											null

									;
								}
								jsonPath_tExtractJSONFields_2 = "author";
								compiledJsonPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
										.getCompiledJsonPath(jsonPath_tExtractJSONFields_2);

								try {

									value_tExtractJSONFields_2 = compiledJsonPath_tExtractJSONFields_2
											.read(row_tExtractJSONFields_2);

									row5.author = value_tExtractJSONFields_2 == null ?

											null

											: value_tExtractJSONFields_2.toString();
								} catch (com.jayway.jsonpath.PathNotFoundException e_tExtractJSONFields_2) {
									globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
											e_tExtractJSONFields_2.getMessage());
									row5.author =

											null

									;
								}
								jsonPath_tExtractJSONFields_2 = "price";
								compiledJsonPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
										.getCompiledJsonPath(jsonPath_tExtractJSONFields_2);

								try {

									value_tExtractJSONFields_2 = compiledJsonPath_tExtractJSONFields_2
											.read(row_tExtractJSONFields_2);

									if (value_tExtractJSONFields_2 != null
											&& !value_tExtractJSONFields_2.toString().isEmpty()) {
										row5.price = ParserUtils.parseTo_Float(value_tExtractJSONFields_2.toString());
									} else {
										row5.price =

												null

										;
									}
								} catch (com.jayway.jsonpath.PathNotFoundException e_tExtractJSONFields_2) {
									globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
											e_tExtractJSONFields_2.getMessage());
									row5.price =

											null

									;
								}
								jsonPath_tExtractJSONFields_2 = "publisher";
								compiledJsonPath_tExtractJSONFields_2 = jsonPathCache_tExtractJSONFields_2
										.getCompiledJsonPath(jsonPath_tExtractJSONFields_2);

								try {

									value_tExtractJSONFields_2 = compiledJsonPath_tExtractJSONFields_2
											.read(row_tExtractJSONFields_2);

									row5.publisher = value_tExtractJSONFields_2 == null ?

											null

											: value_tExtractJSONFields_2.toString();
								} catch (com.jayway.jsonpath.PathNotFoundException e_tExtractJSONFields_2) {
									globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
											e_tExtractJSONFields_2.getMessage());
									row5.publisher =

											null

									;
								}
							} catch (java.lang.Exception ex_tExtractJSONFields_2) {
								globalMap.put("tExtractJSONFields_2_ERROR_MESSAGE",
										ex_tExtractJSONFields_2.getMessage());
								log.error("tExtractJSONFields_2 - " + ex_tExtractJSONFields_2.getMessage());
								System.err.println(ex_tExtractJSONFields_2.getMessage());
								row5 = null;
							}

						}

						isStructError_tExtractJSONFields_2 = false;

						log.debug("tExtractJSONFields_2 - Extracting the record " + nb_line_tExtractJSONFields_2 + ".");
//}

						tos_count_tExtractJSONFields_2++;

						/**
						 * [tExtractJSONFields_2 main ] stop
						 */

						/**
						 * [tExtractJSONFields_2 process_data_begin ] start
						 */

						currentComponent = "tExtractJSONFields_2";

						/**
						 * [tExtractJSONFields_2 process_data_begin ] stop
						 */
// Start of branch "row5"
						if (row5 != null) {

							/**
							 * [tLogRow_4 main ] start
							 */

							currentComponent = "tLogRow_4";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row5", "tExtractJSONFields_2", "tExtractJSONFields_2", "tExtractJSONFields",
									"tLogRow_4", "tLogRow_4", "tLogRow"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row5 - " + (row5 == null ? "" : row5.toLogString()));
							}

///////////////////////		

							String[] row_tLogRow_4 = new String[5];

							if (row5.rank != null) { //
								row_tLogRow_4[0] = String.valueOf(row5.rank);

							} //

							if (row5.title != null) { //
								row_tLogRow_4[1] = String.valueOf(row5.title);

							} //

							if (row5.author != null) { //
								row_tLogRow_4[2] = String.valueOf(row5.author);

							} //

							if (row5.price != null) { //
								row_tLogRow_4[3] = FormatterUtils.formatUnwithE(row5.price);

							} //

							if (row5.publisher != null) { //
								row_tLogRow_4[4] = String.valueOf(row5.publisher);

							} //

							util_tLogRow_4.addRow(row_tLogRow_4);
							nb_line_tLogRow_4++;
							log.info("tLogRow_4 - Content of row " + nb_line_tLogRow_4 + ": "
									+ TalendString.unionString("|", row_tLogRow_4));
//////

//////                    

///////////////////////    			

							tos_count_tLogRow_4++;

							/**
							 * [tLogRow_4 main ] stop
							 */

							/**
							 * [tLogRow_4 process_data_begin ] start
							 */

							currentComponent = "tLogRow_4";

							/**
							 * [tLogRow_4 process_data_begin ] stop
							 */

							/**
							 * [tLogRow_4 process_data_end ] start
							 */

							currentComponent = "tLogRow_4";

							/**
							 * [tLogRow_4 process_data_end ] stop
							 */

						} // End of branch "row5"

						// end for
					}

				} // C_01

				/**
				 * [tExtractJSONFields_2 process_data_end ] start
				 */

				currentComponent = "tExtractJSONFields_2";

				/**
				 * [tExtractJSONFields_2 process_data_end ] stop
				 */

				/**
				 * [tREST_2 process_data_end ] start
				 */

				currentComponent = "tREST_2";

				/**
				 * [tREST_2 process_data_end ] stop
				 */

				/**
				 * [tREST_2 end ] start
				 */

				currentComponent = "tREST_2";

				if (log.isDebugEnabled())
					log.debug("tREST_2 - " + ("Done."));

				ok_Hash.put("tREST_2", true);
				end_Hash.put("tREST_2", System.currentTimeMillis());

				/**
				 * [tREST_2 end ] stop
				 */

				/**
				 * [tExtractJSONFields_2 end ] start
				 */

				currentComponent = "tExtractJSONFields_2";

				globalMap.put("tExtractJSONFields_2_NB_LINE", nb_line_tExtractJSONFields_2);
				log.debug("tExtractJSONFields_2 - Extracted records count: " + nb_line_tExtractJSONFields_2 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row4", 2, 0, "tREST_2",
						"tREST_2", "tREST", "tExtractJSONFields_2", "tExtractJSONFields_2", "tExtractJSONFields",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tExtractJSONFields_2 - " + ("Done."));

				ok_Hash.put("tExtractJSONFields_2", true);
				end_Hash.put("tExtractJSONFields_2", System.currentTimeMillis());

				/**
				 * [tExtractJSONFields_2 end ] stop
				 */

				/**
				 * [tLogRow_4 end ] start
				 */

				currentComponent = "tLogRow_4";

//////

				java.io.PrintStream consoleOut_tLogRow_4 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_4 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_4 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_4);
				}

				consoleOut_tLogRow_4.println(util_tLogRow_4.format().toString());
				consoleOut_tLogRow_4.flush();
//////
				globalMap.put("tLogRow_4_NB_LINE", nb_line_tLogRow_4);
				if (log.isInfoEnabled())
					log.info("tLogRow_4 - " + ("Printed row count: ") + (nb_line_tLogRow_4) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row5", 2, 0,
						"tExtractJSONFields_2", "tExtractJSONFields_2", "tExtractJSONFields", "tLogRow_4", "tLogRow_4",
						"tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_4 - " + ("Done."));

				ok_Hash.put("tLogRow_4", true);
				end_Hash.put("tLogRow_4", System.currentTimeMillis());

				/**
				 * [tLogRow_4 end ] stop
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
				 * [tREST_2 finally ] start
				 */

				currentComponent = "tREST_2";

				/**
				 * [tREST_2 finally ] stop
				 */

				/**
				 * [tExtractJSONFields_2 finally ] start
				 */

				currentComponent = "tExtractJSONFields_2";

				/**
				 * [tExtractJSONFields_2 finally ] stop
				 */

				/**
				 * [tLogRow_4 finally ] start
				 */

				currentComponent = "tLogRow_4";

				/**
				 * [tLogRow_4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tREST_2_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "talendJobLog");
		org.slf4j.MDC.put("_subJobPid", "E5tDM2_" + subJobPidCounter.getAndIncrement());

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

	private SyncInt runningThreadCount = new SyncInt();

	private class SyncInt {
		private int count = 0;

		public synchronized void add(int i) {
			count += i;
		}

		public synchronized int getCount() {
			return count;
		}
	}

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final restApi restApiClass = new restApi();

		int exitCode = restApiClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'restApi' - Done.");
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
		log.info("TalendJob: 'restApi' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_RG3BAOs9Ee6o-JQyryMKwA");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-03-27T07:18:27.023811Z");

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
			java.io.InputStream inContext = restApi.class.getClassLoader()
					.getResourceAsStream("finalproject/restapi_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = restApi.class.getClassLoader()
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
					context.setContextType("url", "id_String");
					if (context.getStringValue("url") == null) {
						context.url = null;
					} else {
						context.url = (String) context.getProperty("url");
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
			if (parentContextMap.containsKey("url")) {
				context.url = (String) parentContextMap.get("url");
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
		log.info("TalendJob: 'restApi' - Started.");
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

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs
		final Thread launchingThread = Thread.currentThread();
		runningThreadCount.add(1);
		new Thread() {
			public void run() {
				mdcInfo.forEach(org.slf4j.MDC::put);

				java.util.Map threadRunResultMap = new java.util.HashMap();
				threadRunResultMap.put("errorCode", null);
				threadRunResultMap.put("status", "");
				threadLocal.set(threadRunResultMap);

				try {
					((java.util.Map) threadLocal.get()).put("errorCode", null);
					tFileInputJSON_3Process(globalMap);
					if (!"failure".equals(((java.util.Map) threadLocal.get()).get("status"))) {
						((java.util.Map) threadLocal.get()).put("status", "end");
					}
				} catch (TalendException e_tFileInputJSON_3) {
					globalMap.put("tFileInputJSON_3_SUBPROCESS_STATE", -1);

					e_tFileInputJSON_3.printStackTrace();

				} catch (java.lang.Error e_tFileInputJSON_3) {
					globalMap.put("tFileInputJSON_3_SUBPROCESS_STATE", -1);
					((java.util.Map) threadLocal.get()).put("status", "failure");
					throw e_tFileInputJSON_3;

				} finally {
					Integer localErrorCode = (Integer) (((java.util.Map) threadLocal.get()).get("errorCode"));
					String localStatus = (String) (((java.util.Map) threadLocal.get()).get("status"));
					if (localErrorCode != null) {
						if (errorCode == null || localErrorCode.compareTo(errorCode) > 0) {
							errorCode = localErrorCode;
						}
					}
					if (!status.equals("failure")) {
						status = localStatus;
					}

					if ("true".equals(((java.util.Map) threadLocal.get()).get("JobInterrupted"))) {
						launchingThread.interrupt();
					}

					runningThreadCount.add(-1);
				}
			}
		}.start();

		runningThreadCount.add(1);
		new Thread() {
			public void run() {
				mdcInfo.forEach(org.slf4j.MDC::put);

				java.util.Map threadRunResultMap = new java.util.HashMap();
				threadRunResultMap.put("errorCode", null);
				threadRunResultMap.put("status", "");
				threadLocal.set(threadRunResultMap);

				try {
					((java.util.Map) threadLocal.get()).put("errorCode", null);
					tJava_1Process(globalMap);
					if (!"failure".equals(((java.util.Map) threadLocal.get()).get("status"))) {
						((java.util.Map) threadLocal.get()).put("status", "end");
					}
				} catch (TalendException e_tJava_1) {
					globalMap.put("tJava_1_SUBPROCESS_STATE", -1);

					e_tJava_1.printStackTrace();

				} catch (java.lang.Error e_tJava_1) {
					globalMap.put("tJava_1_SUBPROCESS_STATE", -1);
					((java.util.Map) threadLocal.get()).put("status", "failure");
					throw e_tJava_1;

				} finally {
					Integer localErrorCode = (Integer) (((java.util.Map) threadLocal.get()).get("errorCode"));
					String localStatus = (String) (((java.util.Map) threadLocal.get()).get("status"));
					if (localErrorCode != null) {
						if (errorCode == null || localErrorCode.compareTo(errorCode) > 0) {
							errorCode = localErrorCode;
						}
					}
					if (!status.equals("failure")) {
						status = localStatus;
					}

					if ("true".equals(((java.util.Map) threadLocal.get()).get("JobInterrupted"))) {
						launchingThread.interrupt();
					}

					runningThreadCount.add(-1);
				}
			}
		}.start();

		boolean interrupted = false;
		while (runningThreadCount.getCount() > 0) {
			try {
				Thread.sleep(10);
			} catch (java.lang.InterruptedException e) {
				interrupted = true;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (interrupted) {
			Thread.currentThread().interrupt();
		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : restApi");
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

		Integer localErrorCode = (Integer) (((java.util.Map) threadLocal.get()).get("errorCode"));
		String localStatus = (String) (((java.util.Map) threadLocal.get()).get("status"));
		if (localErrorCode != null) {
			if (errorCode == null || localErrorCode.compareTo(errorCode) > 0) {
				errorCode = localErrorCode;
			}
		}
		if (localStatus != null && !status.equals("failure")) {
			status = localStatus;
		}

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
		log.info("TalendJob: 'restApi' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

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
 * 126538 characters generated by Talend Cloud Data Management Platform on the
 * 27 March 2024 at 12:48:27 PM IST
 ************************************************************************************************/