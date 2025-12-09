package lu.lamtco.timelink.system;

/**
 * Provides general system-level settings.
 * Currently, this includes the maximum number of working hours
 * a user is allowed to register per day.
 *
 * @version 0.1
 */
public class GeneralSettings {

    private static final int MAXIMUM_ALLOWED_HOURS_OF_WORK = 12;

    /**
     * Returns the maximum number of working hours allowed per day.
     *
     * @return the maximum daily working hours
     */
    public static int getMaximumAllowedHoursOfWork() {
        return MAXIMUM_ALLOWED_HOURS_OF_WORK;
    }
}
