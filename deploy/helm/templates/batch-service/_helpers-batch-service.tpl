{{- define "spring-batch-service.selectorLabels" -}}
app: spring-batch-service
release: {{ .Release.Name }}
{{- end }}

{{- define "spring-batch-service.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "spring-batch-service.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: spring-batch-service
{{- end }}