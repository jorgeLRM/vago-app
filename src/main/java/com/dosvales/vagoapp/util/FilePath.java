package com.dosvales.vagoapp.util;

import java.io.File;

public interface FilePath {
	
	public static final String DOMAIN = "http://localhost:8080";
	public static final String BASE_DIRECTORY = "vagofiles";
	
	public static final String MAGUEY_DIRECTORY = "magueyes";
	public static final String PRODUCER_DIRECTORY = "producers";
	public static final String CUTTING_DIRECTORY = "cuttings";
	public static final String ANALYSIS_DIRECTORY = "analysis";
	public static final String TRANSFER_DIRECTORY = "transfers";
	public static final String PROVIDER_DIRECTORY = "providers";
	public static final String GENERAL_DIRECTORY = "general";
	
	public static final String MAGUEYES_PATH = File.separator + BASE_DIRECTORY + File.separator + MAGUEY_DIRECTORY;
	public static final String PRODUCERS_PATH = File.separator + BASE_DIRECTORY + File.separator + PRODUCER_DIRECTORY;
	public static final String CUTTINGS_PATH = File.separator + BASE_DIRECTORY + File.separator + CUTTING_DIRECTORY;
	public static final String ANALYSIS_PATH = File.separator + BASE_DIRECTORY + File.separator + ANALYSIS_DIRECTORY;
	public static final String TRANSFERS_PATH = File.separator + BASE_DIRECTORY + File.separator + TRANSFER_DIRECTORY;
	public static final String PROVIDERS_PATH = File.separator + BASE_DIRECTORY + File.separator + PROVIDER_DIRECTORY;
	public static final String GENERAL_PATH = File.separator + BASE_DIRECTORY + File.separator + GENERAL_DIRECTORY;
	
}
