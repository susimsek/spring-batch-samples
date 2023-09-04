{{- define "spring-batch-cron-job.selectorLabels" -}}
app: spring-batch-cron-job
release: {{ .Release.Name }}
{{- end }}

{{- define "spring-batch-cron-job.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "spring-batch-cron-job.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: spring-batch-cron-job
{{- end }}