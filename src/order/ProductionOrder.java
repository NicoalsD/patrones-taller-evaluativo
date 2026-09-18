package order;

import formula.Formula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ProductionOrder {
    private final String orderNumber;
    private final LocalDate scheduledDate;
    private final String line;
    private final Formula formula;
    private final double tons;
    private final String destinationSilo;
    private final String traceabilityBatch;
    private final List<String> specialAdditives;
    private final String assignedClient;
    private final String shift;
    private final String observations;
    private final String priority;
    private final String qualityLead;

    private ProductionOrder(Builder builder) {
        this.orderNumber = builder.orderNumber;
        this.scheduledDate = builder.scheduledDate;
        this.line = builder.line;
        this.formula = builder.formula;
        this.tons = builder.tons;
        this.destinationSilo = builder.destinationSilo;
        this.traceabilityBatch = builder.traceabilityBatch;
        this.specialAdditives = List.copyOf(builder.specialAdditives);
        this.assignedClient = builder.assignedClient;
        this.shift = builder.shift;
        this.observations = builder.observations;
        this.priority = builder.priority;
        this.qualityLead = builder.qualityLead;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public String getLine() {
        return line;
    }

    public Formula getFormula() {
        return formula;
    }

    public double getTons() {
        return tons;
    }

    public String getDestinationSilo() {
        return destinationSilo;
    }

    public String getTraceabilityBatch() {
        return traceabilityBatch;
    }

    public List<String> getSpecialAdditives() {
        return specialAdditives;
    }

    public String getAssignedClient() {
        return assignedClient;
    }

    public String getShift() {
        return shift;
    }

    public String getObservations() {
        return observations;
    }

    public String getPriority() {
        return priority;
    }

    public String getQualityLead() {
        return qualityLead;
    }

    public static class Builder {
        private String orderNumber;
        private LocalDate scheduledDate;
        private String line;
        private Formula formula;
        private double tons;
        private String destinationSilo;
        private String traceabilityBatch;
        private List<String> specialAdditives = new ArrayList<>();
        private String assignedClient;
        private String shift;
        private String observations;
        private String priority;
        private String qualityLead;

        public Builder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder scheduledDate(LocalDate scheduledDate) {
            this.scheduledDate = scheduledDate;
            return this;
        }

        public Builder line(String line) {
            this.line = line;
            return this;
        }

        public Builder formula(Formula formula) {
            this.formula = formula;
            return this;
        }

        public Builder tons(double tons) {
            this.tons = tons;
            return this;
        }

        public Builder destinationSilo(String destinationSilo) {
            this.destinationSilo = destinationSilo;
            return this;
        }

        public Builder traceabilityBatch(String traceabilityBatch) {
            this.traceabilityBatch = traceabilityBatch;
            return this;
        }

        public Builder specialAdditives(List<String> specialAdditives) {
            this.specialAdditives = specialAdditives;
            return this;
        }

        public Builder assignedClient(String assignedClient) {
            this.assignedClient = assignedClient;
            return this;
        }

        public Builder shift(String shift) {
            this.shift = shift;
            return this;
        }

        public Builder observations(String observations) {
            this.observations = observations;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder qualityLead(String qualityLead) {
            this.qualityLead = qualityLead;
            return this;
        }

        public ProductionOrder build() {
            if (orderNumber == null || scheduledDate == null || line == null || formula == null
                    || destinationSilo == null || traceabilityBatch == null) {
                throw new IllegalStateException("Missing required fields in production order.");
            }
            if (tons < 5.0 || tons > 200.0) {
                throw new IllegalStateException("Tons out of allowed range (5 to 200): " + tons);
            }
            if (!specialAdditives.isEmpty() && (qualityLead == null || qualityLead.isBlank())) {
                throw new IllegalStateException("Special additives were declared without a quality lead.");
            }
            return new ProductionOrder(this);
        }
    }
}
